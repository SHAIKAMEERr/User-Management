package com.example.user_management.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RoleNotFoundException extends RuntimeException {

    public RoleNotFoundException(String message) {
        super(message);
        log.error("RoleNotFoundException: {}", message);
    }
}
