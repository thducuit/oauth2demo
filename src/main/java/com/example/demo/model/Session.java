package com.example.demo.model;

import java.time.Instant;

public class Session {
    private String sessionId;
    private String userId;
    private String email;
    private Instant createdAt;
    private Instant expiresAt;
    private boolean revoked;

    public Session(String sessionId, String userId, String email, Instant expiresAt) {
        this.sessionId = sessionId;
        this.userId = userId;
        this.email = email;
        this.createdAt = Instant.now();
        this.expiresAt = expiresAt;
        this.revoked = false;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Instant expiresAt) {
        this.expiresAt = expiresAt;
    }

    public boolean isRevoked() {
        return revoked;
    }

    public void setRevoked(boolean revoked) {
        this.revoked = revoked;
    }

    public boolean isValid() {
        return !revoked && Instant.now().isBefore(expiresAt);
    }
}

