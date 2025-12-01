package com.example.demo.model.token;

import java.time.Instant;

public class RefreshToken extends AbstractToken {
    public RefreshToken(String tokenValue, Instant expiresAt) {
        super(tokenValue, expiresAt);
    }
}
