package com.example.demo.service;

import com.example.demo.exception.InvalidNonceException;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.Map;

@Service
public class GoogleOAuthService {

    @Value("${google.client.id}")
    private String clientId;
    @Value("${google.client.secret}")
    private String clientSecret;

    private GoogleIdTokenVerifier verifier;

    public GoogleOAuthService() throws GeneralSecurityException, IOException {

    }

    /**
     * Response format from Google OAuth token endpoint:
     * {
     *   "access_token": "...",
     *   "refresh_token": "...",
     *   "expires_in": 3599,
     *   "scope": "openid email profile",
     *   "token_type": "Bearer",
     *   "id_token": "eyJhbGciOiJSUzI1NiIs...",
     * }
     */
    public Map<String, Object> exchangeCodeForToken(String code, String nonce, String codeVerifier) throws Exception {
        String tokenUrl = "https://oauth2.googleapis.com/token";

        RestTemplate restTemplate = new RestTemplate();

        // Body form-urlencoded
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("code", code);
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);
        body.add("redirect_uri", "https://localhost:3000/auth");
        body.add("grant_type", "authorization_code");
        body.add("code_verifier", codeVerifier);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request =
                new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                tokenUrl,
                HttpMethod.POST,
                request,
                Map.class
        );

        Map<String, Object> tokens = response.getBody();

        if (tokens == null || !tokens.containsKey("id_token")) {
                throw new RuntimeException("No id_token returned from Google.");
        }

        String idToken = tokens.get("id_token").toString();

        if (isNonceInvalid(nonce, idToken)) {
            throw new InvalidNonceException("Unauthorization !!!");
        }

        return tokens;
    }

    private boolean isNonceValid(String nonce, String idToken) throws Exception {
        final GoogleIdToken.Payload payload = verifyToken(idToken);
        return nonce.equals(payload.getNonce());
    }

    private boolean isNonceInvalid(String nonce, String idToken) throws Exception {
        return !isNonceValid(nonce, idToken);
    }

    public GoogleIdToken.Payload verifyToken(String idTokenString) throws Exception {
        this.verifier = new GoogleIdTokenVerifier.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                GsonFactory.getDefaultInstance()
        )
                .setAudience(Collections.singletonList(clientId))
                .build();
        GoogleIdToken idToken = verifier.verify(idTokenString);
        if (idToken == null) {
            throw new IllegalArgumentException("Invalid ID token");
        }
        return idToken.getPayload();
    }

    public Map<String, Object> refreshAccessToken(String refreshToken) {
        String tokenUrl = "https://oauth2.googleapis.com/token";

        RestTemplate restTemplate = new RestTemplate();

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);
        body.add("refresh_token", refreshToken);
        body.add("grant_type", "refresh_token");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request =
                new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                tokenUrl, HttpMethod.POST, request, Map.class
        );

        return response.getBody();   // contains new access_token
    }

}
