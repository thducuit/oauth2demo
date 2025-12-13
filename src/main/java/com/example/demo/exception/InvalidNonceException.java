package com.example.demo.exception;

public class InvalidNonceException extends Exception {
    public InvalidNonceException(String message) {
        super(message);
    }
}
