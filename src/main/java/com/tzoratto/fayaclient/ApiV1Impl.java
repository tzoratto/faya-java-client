package com.tzoratto.fayaclient;

import com.tzoratto.fayaclient.exception.FayaException;
import com.tzoratto.fayaclient.model.Namespace;
import com.tzoratto.fayaclient.model.Token;
import com.tzoratto.fayaclient.model.User;

import javax.ws.rs.core.UriBuilder;
import java.util.HashMap;
import java.util.Map;

class ApiV1Impl implements Api {
    private final String server;
    private final String authorization;
    private final HttpClient httpClient;

    private static final String API_ENDPOINT = "api";
    private static final String API_VERSION = "v1";
    private static final String USER_ENDPOINT = "user";
    private static final String NAMESPACE_ENDPOINT = "namespace";
    private static final String TOKEN_ENDPOINT = "token";
    private static final String CHECK_ENDPOINT = "check";

    private static final String TOKEN_PARAM = "token";
    private static final String NAMESPACE_PARAM = "namespace";

    ApiV1Impl(String server, String authorization) {
        this.server = server;
        this.authorization = authorization;
        this.httpClient = new HttpClientImpl();
    }

    private String fullUrl(String endpoint, String id, Map<String, String> params) {
        UriBuilder builder = UriBuilder.fromPath(server)
                .path(API_ENDPOINT)
                .path(API_VERSION)
                .path(endpoint);

        if (id != null) {
            builder.path(id);
        }
        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet())
            {
                builder.queryParam(entry.getKey(), entry.getValue());
            }
        }

        return builder.build().toString();
    }

    @Override
    public void createNamespace(Namespace namespace) throws FayaException {
        String url = fullUrl(NAMESPACE_ENDPOINT, null, null);
        Namespace ns = this.httpClient.post(url, authorization, namespace, Namespace.class).getData();
        namespace.setId(ns.getId());
        namespace.setDescription(ns.getDescription());
        namespace.setName(ns.getName());
        namespace.setUser(ns.getUser());
    }

    @Override
    public void updateNamespace(Namespace namespace) throws FayaException {
        String url = fullUrl(NAMESPACE_ENDPOINT, namespace.getId(), null);
        Namespace ns = this.httpClient.put(url, authorization, namespace, Namespace.class).getData();
        namespace.setId(ns.getId());
        namespace.setDescription(ns.getDescription());
        namespace.setName(ns.getName());
        namespace.setUser(ns.getUser());
    }

    @Override
    public void createToken(Token token) throws FayaException {
        String url = fullUrl(TOKEN_ENDPOINT, null, null);
        Token t = this.httpClient.post(url, authorization, token, Token.class).getData();
        token.setId(t.getId());
        token.setDescription(t.getDescription());
        token.setActive(t.getActive());
        token.setNamespace(t.getNamespace());
        token.setCount(t.getCount());
        token.setCreatedAt(t.getCreatedAt());
        token.setEndsAt(t.getEndsAt());
        token.setPool(t.getPool());
        token.setUpdatedAt(t.getUpdatedAt());
        token.setValue(t.getValue());
        token.setStartsAt(t.getStartsAt());
    }

    @Override
    public void updateToken(Token token) throws FayaException {
        String url = fullUrl(TOKEN_ENDPOINT, token.getId(), null);
        Token t = this.httpClient.put(url, authorization, token, Token.class).getData();
        token.setId(t.getId());
        token.setDescription(t.getDescription());
        token.setActive(t.getActive());
        token.setNamespace(t.getNamespace());
        token.setCount(t.getCount());
        token.setCreatedAt(t.getCreatedAt());
        token.setEndsAt(t.getEndsAt());
        token.setPool(t.getPool());
        token.setUpdatedAt(t.getUpdatedAt());
        token.setValue(t.getValue());
        token.setStartsAt(t.getStartsAt());
    }

    @Override
    public void deleteUser(User user) throws FayaException {
        String url = fullUrl(USER_ENDPOINT, user.getId(), null);
        this.httpClient.delete(url, authorization, Void.class);
    }

    @Override
    public void deleteNamespace(Namespace namespace) throws FayaException {
        String url = fullUrl(NAMESPACE_ENDPOINT, namespace.getId(), null);
        this.httpClient.delete(url, authorization, Void.class);
    }

    @Override
    public void deleteToken(Token token) throws FayaException {
        String url = fullUrl(TOKEN_ENDPOINT, token.getId(), null);
        this.httpClient.delete(url, authorization, Void.class);
    }

    @Override
    public User getUser(String id) throws FayaException {
        String url = fullUrl(USER_ENDPOINT, id, null);
        return this.httpClient.get(url, authorization, User.class).getData();
    }

    @Override
    public Namespace getNamespace(String id) throws FayaException {
        String url = fullUrl(NAMESPACE_ENDPOINT, id, null);
        return this.httpClient.get(url, authorization, Namespace.class).getData();
    }

    @Override
    public Token getToken(String id) throws FayaException {
        String url = fullUrl(TOKEN_ENDPOINT, id, null);
        return this.httpClient.get(url, authorization, Token.class).getData();
    }

    @Override
    public boolean check(String token, String namespace) throws FayaException {
        Map<String, String> params = new HashMap<>();
        params.put(TOKEN_PARAM, token);
        params.put(NAMESPACE_PARAM, namespace);
        String url = fullUrl(CHECK_ENDPOINT, null, params);
        return this.httpClient.get(url, authorization, Void.class).isSuccessful();
    }
}
