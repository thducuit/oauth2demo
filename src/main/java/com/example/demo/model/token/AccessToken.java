package com.example.demo.model.token;

import java.time.Instant;

public class AccessToken extends AbstractToken {
    public AccessToken(String tokenValue, Instant expiresAt) {
        super(tokenValue, expiresAt);
    }
}
