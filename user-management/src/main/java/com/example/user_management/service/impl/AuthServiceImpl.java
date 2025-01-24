package com.example.user_management.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.user_management.dao.UserEntity;
import com.example.user_management.dto.AuthRequestDTO;
import com.example.user_management.dto.AuthResponseDTO;
import com.example.user_management.repository.UserRepository;
import com.example.user_management.service.AuthService;
import com.example.user_management.util.HmacSHA256Utils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    // Secret key used for hashing passwords (you should load this from a secure config)
    private static final String SECRET_KEY = "your-secret-key";

    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    @Override
    public AuthResponseDTO login(AuthRequestDTO authRequestDTO) {
    	
        log.info("Attempting login for email: {}", authRequestDTO.getEmail());

        return userRepository.findByEmail(authRequestDTO.getEmail())
                .map(user -> {
                    // Check if the stored password is hashed or plain-text
                    if (isPasswordHashed(user.getPassword())) {
                        // Hash the input password
                        String hashedPassword = HmacSHA256Utils.generateHmacSHA256(
                                authRequestDTO.getPassword(), SECRET_KEY);
                        
                        log.debug("Hashed input password: {}", hashedPassword);
                        log.debug("Stored password in DB: {}", user.getPassword());

                        if (user.getPassword().equals(hashedPassword)) {
                            return generateAuthResponse(user);
                        } else {
                            log.error("Invalid password for email: {}", authRequestDTO.getEmail());
                            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
                        }
                    } else {
                        // Plaintext password, just compare directly
                        if (user.getPassword().equals(authRequestDTO.getPassword())) {
                            return generateAuthResponse(user);
                        } else {
                            log.error("Invalid password for email: {}", authRequestDTO.getEmail());
                            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
                        }
                    }
                })
                .orElseThrow(() -> {
                    log.error("User not found with email: {}", authRequestDTO.getEmail());
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
                });
    }

    // Helper method to determine if the password is hashed
    private boolean isPasswordHashed(String password) {
        // Simple check to see if the password is a hash. Adjust based on your actual hashing scheme
        return password != null && password.length() > 32; // A basic example: hashes are typically long (e.g., SHA-256)
    }

    // Method to generate AuthResponseDTO
    private AuthResponseDTO generateAuthResponse(UserEntity user) {
        log.info("Login successful for email: {}", user.getEmail());

        AuthResponseDTO response = new AuthResponseDTO();
        response.setAccessToken("generated-access-token"); // Replace with actual token generation logic
        response.setRefreshToken("generated-refresh-token"); // Replace with actual refresh token generation
        response.setExpiresIn(3600L); // Example: token expires in 1 hour

        return response;
    }
}
