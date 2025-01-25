package com.example.user_management.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.user_management.dto.AuthRequestDTO;
import com.example.user_management.dto.AuthResponseDTO;
import com.example.user_management.service.AuthService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/auth")
@Slf4j
@Validated
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Handles user login requests.
     *
     * @param authRequestDTO The authentication request containing email and password.
     * @return The authentication response containing token or success message.
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody AuthRequestDTO authRequestDTO) {
        log.info("Login request received for email: {}", authRequestDTO.getEmail());

        try {
            // Authenticate user and generate response
            AuthResponseDTO authResponse = authService.login(authRequestDTO);

            log.info("Login successful for email: {}", authRequestDTO.getEmail());

            return ResponseEntity.ok(authResponse);
        } catch (AuthenticationException e) {
            log.error("Login failed for email: {}", authRequestDTO.getEmail(), e);

            // Return unauthorized response for authentication failures
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthResponseDTO(e.getMessage()));
        } catch (Exception e) {
            log.error("Unexpected error during login for email: {}", authRequestDTO.getEmail(), e);

            // Return internal server error for unexpected exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
