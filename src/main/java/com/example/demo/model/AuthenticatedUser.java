package com.example.demo.model;

public record AuthenticatedUser(
        String userId,
        String sessionId
) {}
