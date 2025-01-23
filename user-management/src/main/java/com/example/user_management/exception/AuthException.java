package com.example.user_management.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuthException extends RuntimeException {

    public AuthException(String message) {
        super(message);
        log.error("AuthException: {}", message);
    }
}
