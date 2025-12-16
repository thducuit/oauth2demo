package com.example.demo.model;

import com.example.demo.model.token.AccessToken;
import com.example.demo.model.token.RefreshToken;

public class AuthResponse {
    private AccessToken accessToken; // time expire 15 mins
    private RefreshToken refreshToken; // time expire 1 day

    public AuthResponse(AccessToken accessToken, RefreshToken refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public AccessToken getAccessToken() {
        return accessToken;
    }

    public RefreshToken getRefreshToken() {
        return refreshToken;
    }
}
