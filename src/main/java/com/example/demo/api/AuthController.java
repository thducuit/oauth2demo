package com.example.demo.api;

import com.example.demo.dto.LoginRequest;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import com.example.demo.service.GoogleOAuthService;
import com.example.demo.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

@RestController
public class AuthController {

    @Autowired
    private GoogleOAuthService googleOAuthService;

    @Autowired
    private SessionService sessionService;

    @PostMapping("/auth/login")
    public Map<String, Object> login(@RequestBody LoginRequest request) throws Exception {
        return googleOAuthService.exchangeCodeForToken(
                request.getCode(),
                request.getNonce(),
                request.getCodeVerifier()
        );
    }

    @PostMapping("/auth/logout")
    public ResponseEntity<Map<String, String>> logout(@RequestHeader(value = "Authorization", required = false) String authHeader) {
        if (authHeader == null || authHeader.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Authorization header is required"));
        }
        
        // Extract sessionId from token
        String token = authHeader.startsWith("Bearer ") 
                ? authHeader.substring(7) 
                : authHeader;
        
        try {
            String sessionId = googleOAuthService.extractSessionIdFromToken(token);
            if (sessionId != null && sessionService.revokeSession(sessionId)) {
                return ResponseEntity.ok(Map.of("message", "Session revoked successfully"));
            }
            return ResponseEntity.badRequest().body(Map.of("message", "Invalid session"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", "Invalid token"));
        }
    }

    @PostMapping("/auth/revoke/{sessionId}")
    public ResponseEntity<Map<String, String>> revokeSession(@PathVariable String sessionId) {
        if (sessionService.revokeSession(sessionId)) {
            return ResponseEntity.ok(Map.of("message", "Session revoked successfully"));
        }
        return ResponseEntity.badRequest().body(Map.of("message", "Session not found or already revoked"));
    }

    @GetMapping("/test")
    public ResponseEntity<Map<String, String>> test() {
        // TODO: validate access token by filter chain
        return ResponseEntity.ok(Map.of("message", "Test successful"));
    }

    @GetMapping("/dev")
    public ResponseEntity<Map<String, Object>> dev() {
        return ResponseEntity.ok(Map.of(
            "message", "Load balancer test endpoint",
            "timestamp", System.currentTimeMillis(),
            "instance", System.getenv().getOrDefault("HOSTNAME", "unknown")
        ));
    }
}

