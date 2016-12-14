package com.tzoratto.fayaclient;

import com.tzoratto.fayaclient.exception.FayaException;
import com.tzoratto.fayaclient.model.Response;

interface HttpClient {
    <T> Response<T> get(String endpoint, String authorization, final Class<T> clazz) throws FayaException;
    <T> Response<T> post(String endpoint, String authorization, T data, final Class<T> clazz) throws FayaException;
    <T> Response<T> put(String endpoint, String authorization, T data, final Class<T> clazz) throws FayaException;
    <T> Response<T> delete(String endpoint, String authorization, final Class<T> clazz) throws FayaException;
}
