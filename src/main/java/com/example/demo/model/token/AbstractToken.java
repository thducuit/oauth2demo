package com.example.demo.model.token;

import java.time.Instant;

public abstract class AbstractToken {
    private final String tokenValue;
//    private final Instant issuedAt;
    private final Instant expiresAt;

    protected AbstractToken(String tokenValue, Instant expiresAt) {
        this.tokenValue = tokenValue;
        this.expiresAt = expiresAt;
    }

    public String getTokenValue() {
        return tokenValue;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }
}
