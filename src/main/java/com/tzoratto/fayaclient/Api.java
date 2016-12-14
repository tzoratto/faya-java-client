package com.tzoratto.fayaclient;

import com.tzoratto.fayaclient.exception.FayaException;
import com.tzoratto.fayaclient.model.Namespace;
import com.tzoratto.fayaclient.model.Token;
import com.tzoratto.fayaclient.model.User;

interface Api {
    void createNamespace(Namespace namespace) throws FayaException;
    void updateNamespace(Namespace namespace) throws FayaException;
    void createToken(Token token) throws FayaException;
    void updateToken(Token token) throws FayaException;
    void deleteUser(User user) throws FayaException;
    void deleteNamespace(Namespace namespace) throws FayaException;
    void deleteToken(Token token) throws FayaException;
    User getUser(String id) throws FayaException;
    Namespace getNamespace(String id) throws FayaException;
    Token getToken(String id) throws FayaException;
    boolean check(String token, String namespace) throws FayaException;
}
