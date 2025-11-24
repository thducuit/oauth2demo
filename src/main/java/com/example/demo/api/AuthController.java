package com.example.demo.api;

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

    @GetMapping("/auth/login")
    public Map<String, Object> login(@RequestParam String code) {
        // TODO: check none by id token from google
        return googleOAuthService.exchangeCodeForToken(code);
    }
}

class AuthResponse {
    private String accessToken; // time expire 15 mins
    private String refreshToken; // time expire 1 day
}
