package com.tzoratto.fayaclient;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tzoratto.fayaclient.exception.FayaException;
import com.tzoratto.fayaclient.model.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;

class HttpClientImpl implements HttpClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientImpl.class);

    private final Client client;
    private final ObjectMapper mapper;

    HttpClientImpl() {
        this.client = ClientBuilder.newClient();
        this.mapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, false);
    }

    @Override
    public <T> Response<T> get(String endpoint, String authorization, final Class<T> clazz) throws FayaException {
        javax.ws.rs.core.Response response = this.prepareRequest(endpoint, authorization)
                .get();
        return getResponse(response, clazz);
    }

    @Override
    public <T> Response<T> post(String endpoint, String authorization, T data, final Class<T> clazz) throws FayaException {
        javax.ws.rs.core.Response response = this.prepareRequest(endpoint, authorization)
                .post(Entity.json(objectToJsonString(data)));
        return getResponse(response, clazz);
    }

    @Override
    public <T> Response<T> put(String endpoint, String authorization, T data, final Class<T> clazz) throws FayaException {
        javax.ws.rs.core.Response response = this.prepareRequest(endpoint, authorization)
                .put(Entity.json(objectToJsonString(data)));
        return getResponse(response, clazz);
    }

    @Override
    public <T> Response<T> delete(String endpoint, String authorization, final Class<T> clazz) throws FayaException {
        javax.ws.rs.core.Response response = this.prepareRequest(endpoint, authorization)
                .delete();
        return getResponse(response, clazz);
    }

    private <T> String objectToJsonString(T data) throws FayaException {
        String dataString;
        try {
            dataString = mapper.writer().writeValueAsString(data);
            LOGGER.debug(dataString);
        } catch (JsonProcessingException e) {
            throw new FayaException("Error during serialization", e);
        }
        return dataString;
    }

    private Invocation.Builder prepareRequest(String endpoint, String authorization) throws FayaException {
        LOGGER.debug(endpoint);
        String base64Authorization;
        try {
            base64Authorization = Base64.getEncoder().encodeToString(authorization.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            throw new FayaException("Bad credentials", e);
        }
        return this.client.target(endpoint)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .header("Authorization", "Basic: " + base64Authorization);
    }

    private void handleError(javax.ws.rs.core.Response response) throws FayaException {
        if (response.getStatus() != 200) {
            try {
                String resString = response.readEntity(String.class);
                LOGGER.debug(resString);
                Response res = mapper.readerFor(Response.class).readValue(resString);
                throw new FayaException(res.getMessage() + " - " + res.getData());
            } catch (IOException e) {
                throw new FayaException("An error occurred. HTTP code : " + response.getStatus(), e);
            }
        }
    }

    private <T> Response<T> getResponse(javax.ws.rs.core.Response response, final Class<T> clazz) throws FayaException {
        this.handleError(response);
        try {
            String res = response.readEntity(String.class);
            LOGGER.debug(res);
            return mapper.readerFor(mapper.getTypeFactory().constructParametricType(Response.class, clazz)).readValue(res);
        } catch (IOException e) {
            throw new FayaException("Error during deserialization", e);
        }
    }
}
