package com.tzoratto.fayaclient;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.tzoratto.fayaclient.exception.FayaException;
import com.tzoratto.fayaclient.model.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;

class HttpClientImpl implements HttpClient {
    private static final Logger logger = LoggerFactory.getLogger(HttpClientImpl.class);

    private final Client client;
    private final ObjectMapper mapper;

    HttpClientImpl() {
        this.client = Client.create();
        this.mapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, false);
    }

    public <T> Response<T> get(String endpoint, String authorization, final Class<T> clazz) throws FayaException {
        ClientResponse response = this.prepareRequest(endpoint, authorization)
                .get(ClientResponse.class);
        return getResponse(response, clazz);
    }

    public <T> Response<T> post(String endpoint, String authorization, T data, final Class<T> clazz) throws FayaException {
        ClientResponse response = this.prepareRequest(endpoint, authorization)
                .post(ClientResponse.class, objectToJsonString(data));
        return getResponse(response, clazz);
    }

    public <T> Response<T> put(String endpoint, String authorization, T data, final Class<T> clazz) throws FayaException {
        ClientResponse response = this.prepareRequest(endpoint, authorization)
                .put(ClientResponse.class, objectToJsonString(data));
        return getResponse(response, clazz);
    }

    public <T> Response<T> delete(String endpoint, String authorization, final Class<T> clazz) throws FayaException {
        ClientResponse response = this.prepareRequest(endpoint, authorization)
                .delete(ClientResponse.class);
        return getResponse(response, clazz);
    }

    private <T> String objectToJsonString(T data) throws FayaException {
        String dataString;
        try {
            dataString = mapper.writer().writeValueAsString(data);
            logger.debug(dataString);
        } catch (JsonProcessingException e) {
            throw new FayaException("Error during serialization", e);
        }
        return dataString;
    }

    private WebResource.Builder prepareRequest(String endpoint, String authorization) throws FayaException {
        logger.info(endpoint);
        String base64Authorization;
        try {
            base64Authorization = Base64.getEncoder().encodeToString(authorization.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            throw new FayaException("Bad credentials", e);
        }
        return this.client.resource(endpoint)
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .header("Authorization", "Basic: " + base64Authorization);
    }

    private void handleError(ClientResponse response) throws FayaException {
        if (response.getStatus() != 200) {
            try {
                String resString = response.getEntity(String.class);
                logger.debug(resString);
                Response res = mapper.readerFor(Response.class).readValue(resString);
                throw new FayaException(res.getMessage() + " - " + res.getData());
            } catch (IOException e) {
                throw new FayaException("An error occurred. HTTP code : " + response.getStatus(), e);
            }
        }
    }

    private <T> Response<T> getResponse(ClientResponse response, final Class<T> clazz) throws FayaException {
        this.handleError(response);
        try {
            String res = response.getEntity(String.class);
            logger.debug(res);
            return mapper.readerFor(mapper.getTypeFactory().constructParametricType(Response.class, clazz)).readValue(res);
        } catch (IOException e) {
            throw new FayaException("Error during deserialization", e);
        }
    }
}
