package com.example.user_management.util;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ValidationUtils {

    public static boolean isValidEmail(String email) {
        log.info("Validating email format: {}", email);
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    public static boolean isValidPassword(String password) {
        log.info("Validating password strength");
        return password != null && password.length() >= 8;
    }
}

