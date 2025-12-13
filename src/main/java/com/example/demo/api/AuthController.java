package com.example.demo.api;

import com.example.demo.dto.LoginRequest;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import com.example.demo.service.GoogleOAuthService;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
public class AuthController {

    @Autowired
    private GoogleOAuthService googleOAuthService;

    @PostMapping("/auth/login")
    public Map<String, Object> login(@RequestBody LoginRequest request) throws Exception {
        return googleOAuthService.exchangeCodeForToken(
                request.getCode(),
                request.getNonce(),
                request.getCodeVerifier()
        );
    }   
}

