package com.tzoratto.fayaclient.model;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

public class User {
    private static class Local {
        private String email;
        private boolean valid;
        private Instant date;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public boolean getValid() {
            return valid;
        }

        public void setValid(boolean valid) {
            this.valid = valid;
        }

        public Instant getDate() {
            return date;
        }

        public void setDate(Instant date) {
            this.date = date;
        }
    }

    @JsonProperty("_id")
    private String id;
    private Local local;
    private Instant lastAccess;
    private Instant createdAt;
    private boolean admin;

    public User() {
        this.local = new Local();
    }

    public User(String id) {
        this();
        this.id = id;
    }

    public Local getLocal() {
        return local;
    }

    public void setLocal(Local local) {
        this.local = local;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocalEmail() {
        return local.email;
    }

    public void setLocalEmail(String localEmail) {
        this.local.email = localEmail;
    }

    public boolean getLocalValid() {
        return local.valid;
    }

    public void setLocalValid(boolean localValid) {
        this.local.valid = localValid;
    }

    public Instant getLocalDate() {
        return local.date;
    }

    public void setLocalDate(Instant localDate) {
        this.local.date = localDate;
    }

    public Instant getLastAccess() {
        return lastAccess;
    }

    public void setLastAccess(Instant lastAccess) {
        this.lastAccess = lastAccess;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
}
