package com.tzoratto.fayaclient.model;


import com.fasterxml.jackson.annotation.JsonProperty;

public class Namespace {
    @JsonProperty("_id")
    private String id;
    private String user;
    private String name;
    private String description;

    public Namespace() {
        // Empty constructor
    }

    public Namespace(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void populate(Namespace n) {
        if (n != null) {
            this.id = n.id;
            this.description = n.description;
            this.name = n.name;
            this.user = n.user;
        }
    }
}
