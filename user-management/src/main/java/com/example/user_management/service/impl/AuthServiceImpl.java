package com.example.user_management.service.impl;


import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.user_management.dao.UserEntity;
import com.example.user_management.dto.AuthRequestDTO;
import com.example.user_management.dto.AuthResponseDTO;
import com.example.user_management.repository.UserRepository;
import com.example.user_management.service.AuthService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AuthResponseDTO login(AuthRequestDTO authRequestDTO) {
        log.info("Attempting login for email: {}", authRequestDTO.getEmail());

        // Log the beginning of the login process
        try {
            return userRepository.findByEmail(authRequestDTO.getEmail())
                    .map(user -> {
                        log.info("User found with email: {}", authRequestDTO.getEmail());

                        if (isPasswordHashed(user.getPassword())) {
                            log.debug("Password is hashed. Attempting to match hashed password for email: {}", authRequestDTO.getEmail());
                            // Hash the input password and compare it to the stored one if password is hashed
                            if (passwordEncoder.matches(authRequestDTO.getPassword(), user.getPassword())) {
                                log.info("Password matches for email: {}", authRequestDTO.getEmail());
                                return generateAuthResponse(user);
                            } else {
                                log.error("Invalid password for email: {}", authRequestDTO.getEmail());
                                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password");
                            }
                        } else {
                            log.debug("Password is plain text. Attempting direct password match for email: {}", authRequestDTO.getEmail());
                            // Directly compare plain text password if not hashed
                            if (authRequestDTO.getPassword().equals(user.getPassword())) {
                                log.info("Password matches for email: {}", authRequestDTO.getEmail());
                                return generateAuthResponse(user);
                            } else {
                                log.error("Invalid password for email: {}", authRequestDTO.getEmail());
                                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password");
                            }
                        }
                    })
                    .orElseThrow(() -> {
                        log.error("User not found with email: {}", authRequestDTO.getEmail());
                        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password");
                    });
        } catch (Exception e) {
            log.error("Error occurred during login attempt for email: {}", authRequestDTO.getEmail(), e);
            throw e;  // Rethrow the exception after logging it
        }
    }

    // Method to check if the password is hashed (for bcrypt, hash length is 60 characters)
    private boolean isPasswordHashed(String password) {
        return password != null && password.length() == 60;  // bcrypt hashes are always 60 characters
    }

    // Method to generate AuthResponseDTO
    private AuthResponseDTO generateAuthResponse(UserEntity user) {
        log.info("Login successful for email: {}", user.getEmail());

        AuthResponseDTO response = new AuthResponseDTO();
        response.setAccessToken("mock-access-token-" + user.getId()); // Replace with actual token generation logic
        response.setRefreshToken("mock-refresh-token-" + user.getId()); // Replace with actual refresh token logic
        response.setExpiresIn(3600L); // Example: token expires in 1 hour

        log.debug("Generated tokens for user: {}", user.getEmail());
        return response;
    }
}
