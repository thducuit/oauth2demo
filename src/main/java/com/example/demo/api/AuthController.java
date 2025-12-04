package com.example.demo.api;

import com.example.demo.model.token.AccessToken;
import com.example.demo.model.token.RefreshToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Map;
import com.example.demo.service.GoogleOAuthService;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
public class AuthController {

    @Autowired
    private GoogleOAuthService googleOAuthService;

    @PostMapping("/auth/login")
    // TODO: them PKCE code challenge
    public Map<String, Object> login(@RequestBody LoginRequest request) {
        return googleOAuthService.exchangeCodeForToken(
                request.getCode(),
                request.getNonce()
        );
    }   
}

