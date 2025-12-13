package com.example.demo.util;

import org.springframework.stereotype.Component;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

@Component
public class PkceHelper {

    private static final int CODE_VERIFIER_LENGTH = 96; // Between 43 and 128 chars (96 is common & secure)
    private static final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-._~";

    /**
     * Generates a cryptographically secure code_verifier (43–128 chars)
     */
    public String generateCodeVerifier() {
        SecureRandom sr = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < CODE_VERIFIER_LENGTH; i++) {
            int randomIndex = sr.nextInt(CHARS.length());
            sb.append(CHARS.charAt(randomIndex));
        }
        return sb.toString();
    }

    /**
     * Generates code_challenge using S256 method:
     * BASE64URL-ENCODE(SHA256(ASCII(codeVerifier)))
     */
    public String generateCodeChallenge(String codeVerifier) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(codeVerifier.getBytes(java.nio.charset.StandardCharsets.US_ASCII));

            // Base64 URL encode (no padding, replace + with -, / with _, remove =)
            return Base64.getUrlEncoder()
                    .withoutPadding()
                    .encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not available", e);
        }
    }

    /**
     * Convenience method: returns both verifier and challenge
     */
    public PkcePair generatePair() {
        String verifier = generateCodeVerifier();
        String challenge = generateCodeChallenge(verifier);
        return new PkcePair(verifier, challenge);
    }
    public record PkcePair(String codeVerifier, String codeChallenge) {}
};
