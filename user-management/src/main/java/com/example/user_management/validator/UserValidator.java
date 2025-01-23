package com.example.user_management.validator;

import com.example.user_management.dto.UserRequestDTO;
import com.example.user_management.exception.ValidationException;  // Use custom exception
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
@Slf4j
public class UserValidator {

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";
    private static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";

    public void validateUserRequest(UserRequestDTO userRequestDTO) {
        log.info("Validating user request: {}", userRequestDTO);

        // Email validation
        if (userRequestDTO.getEmail() == null || !Pattern.matches(EMAIL_REGEX, userRequestDTO.getEmail())) {
            log.error("Invalid email: {}", userRequestDTO.getEmail());
            throw new ValidationException("Invalid email format");
        }

        // Password validation
        if (userRequestDTO.getPassword() == null || !Pattern.matches(PASSWORD_REGEX, userRequestDTO.getPassword())) {
            log.error("Invalid password for user: {}", userRequestDTO.getEmail());
            throw new ValidationException("Password must be at least 8 characters long, contain uppercase, lowercase, numbers, and special characters");
        }

        // Name validation
        if (userRequestDTO.getUsername() == null || userRequestDTO.getUsername().trim().isEmpty()) {
            log.error("Invalid name for user");
            throw new ValidationException("Name cannot be null or empty");
        }

        log.info("User request validated successfully");
    }
}
