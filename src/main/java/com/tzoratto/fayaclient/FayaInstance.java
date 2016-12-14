package com.tzoratto.fayaclient;

import com.tzoratto.fayaclient.exception.FayaException;
import com.tzoratto.fayaclient.model.Namespace;
import com.tzoratto.fayaclient.model.Token;
import com.tzoratto.fayaclient.model.User;

public class FayaInstance {
    private final Api api;

    public FayaInstance(String server, String authorization) {
        this.api = new ApiV1Impl(server, authorization);
    }

    public void save(Namespace namespace) throws FayaException {
        if (namespace == null) {
            throw new IllegalArgumentException("Can not save null object");
        }
        if (namespace.getId() != null) {
            this.api.updateNamespace(namespace);
        } else {
            this.api.createNamespace(namespace);
        }
    }

    public void save(Token token) throws FayaException {
        if (token == null) {
            throw new IllegalArgumentException("Can not save null object");
        }
        if (token.getId() != null) {
            this.api.updateToken(token);
        } else {
            this.api.createToken(token);
        }
    }

    public void delete(User user) throws FayaException {
        if (user != null && user.getId() != null) {
            this.api.deleteUser(user);
        } else {
            throw new IllegalArgumentException("Can not delete user without id");
        }
    }

    public void delete(Namespace namespace) throws FayaException {
        if (namespace != null && namespace.getId() != null) {
            this.api.deleteNamespace(namespace);
        } else {
            throw new IllegalArgumentException("Can not delete namespace without id");
        }
    }

    public void delete(Token token) throws FayaException {
        if (token != null && token.getId() != null) {
            this.api.deleteToken(token);
        } else {
            throw new IllegalArgumentException("Can not delete token without id");
        }
    }

    public User getUser(String id) throws FayaException {
        if (id != null) {
            return this.api.getUser(id);
        } else {
            throw new IllegalArgumentException("Can not get user without id");
        }
    }

    public Namespace getNamespace(String id) throws FayaException {
        if (id != null) {
            return this.api.getNamespace(id);
        } else {
            throw new IllegalArgumentException("Can not get namesapce without id");
        }
    }

    public Token getToken(String id) throws FayaException {
        if (id != null) {
            return this.api.getToken(id);
        } else {
            throw new IllegalArgumentException("Can not get token without id");
        }
    }

    public boolean check(String token, String namespace) throws FayaException {
        if (token != null && namespace != null) {
            return this.api.check(token, namespace);
        } else {
            throw new IllegalArgumentException("Parameters can not be null");
        }
    }
}
