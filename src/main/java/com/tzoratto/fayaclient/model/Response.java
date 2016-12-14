package com.tzoratto.fayaclient.model;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

public class Response<T> {
    public enum Status {
        SUCCESS,
        FAIL,
        ERROR;

        private static final Map<String, Status> statusMap = new HashMap<>(3);

        static {
            statusMap.put("success", SUCCESS);
            statusMap.put("fail", FAIL);
            statusMap.put("error", ERROR);
        }

        @JsonCreator
        public static Status forValue(String value) {
            if (value != null) {
                return statusMap.get(value.toLowerCase());
            } else {
                return null;
            }
        }

        @JsonValue
        public String toValue() {
            for (Map.Entry<String, Status> entry : statusMap.entrySet()) {
                if (entry.getValue() == this)
                    return entry.getKey();
            }

            return null;
        }
    }

    private Status status;
    private String message;
    private T data;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccessful() {
        return this.status == Status.SUCCESS;
    }
}
