package com.tzoratto.fayaclient.model;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

public class Token {
    @JsonProperty("_id")
    private String id;
    private String namespace;
    private String value;
    private String description;
    private int count;
    private boolean active = true;
    private Instant startsAt;
    private Instant endsAt;
    private Integer pool;
    private Instant createdAt;
    private Instant updatedAt;

    public Token() {
        // Empty constructor
    }

    public Token(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public void setNamespace(Namespace namespace) {
        if (namespace != null) {
            this.namespace = namespace.getId();
        }
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Instant getStartsAt() {
        return startsAt;
    }

    public void setStartsAt(Instant startsAt) {
        this.startsAt = startsAt;
    }

    public Instant getEndsAt() {
        return endsAt;
    }

    public void setEndsAt(Instant endsAt) {
        this.endsAt = endsAt;
    }

    public Integer getPool() {
        return pool;
    }

    public void setPool(Integer pool) {
        this.pool = pool;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void populate(Token t) {
        if (t != null) {
            this.id = t.id;
            this.description = t.description;
            this.active = t.active;
            this.namespace = t.namespace;
            this.count = t.count;
            this.createdAt = t.createdAt;
            this.endsAt = t.endsAt;
            this.pool = t.pool;
            this.updatedAt = t.updatedAt;
            this.value = t.value;
            this.startsAt = t.startsAt;
        }
    }
}
