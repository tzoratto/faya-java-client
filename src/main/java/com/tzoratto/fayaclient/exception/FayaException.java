package com.tzoratto.fayaclient.exception;

public class FayaException extends Exception {
    public FayaException(String message) {
        super(message);
    }

    public FayaException(String message, Exception cause) {
        super(message, cause);
    }
}
