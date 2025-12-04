package com.example.demo.dto;

public class LoginRequest {
    private String code;
    private String nonce;

    // getters & setters
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }

    public String getNonce() {
        return nonce;
    }
    public void setNonce(String nonce) {
        this.nonce = nonce;
    }
}
