package com.example.demo.service;

import com.example.demo.model.AuthResponse;
import com.example.demo.model.token.AccessToken;
import com.example.demo.model.token.RefreshToken;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class AuthService {
    public AuthResponse authorize() {
        return new AuthResponse(
                new AccessToken("value", Instant.now().plus(15,ChronoUnit.MINUTES)),
                new RefreshToken("value", Instant.now().plus(1, ChronoUnit.HOURS)));
    }

}