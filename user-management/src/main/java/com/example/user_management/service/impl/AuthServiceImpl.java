package com.example.user_management.service.impl;

import org.springframework.stereotype.Service;

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
                    // Use HmacSHA256Utils.generateHmacSHA256() to hash the password
                    String hashedPassword = HmacSHA256Utils.generateHmacSHA256(
                        authRequestDTO.getPassword(), SECRET_KEY);
                    
                    if (user.getPassword().equals(hashedPassword)) {
                        log.info("Login successful for email: {}", authRequestDTO.getEmail());

                        // Create and return AuthResponseDTO
                        AuthResponseDTO response = new AuthResponseDTO();
                        response.setAccessToken("generated-access-token"); // Replace with actual token generation
                        response.setRefreshToken("generated-refresh-token"); // Replace with actual refresh token
                        response.setExpiresIn(3600L); // Example: token expires in 1 hour
                        return response;

                    } else {
                        log.error("Invalid password for email: {}", authRequestDTO.getEmail());
                        throw new RuntimeException("Invalid credentials");
                    }
                })
                .orElseThrow(() -> {
                    log.error("User not found with email: {}", authRequestDTO.getEmail());
                    return new RuntimeException("Invalid credentials");
                });
    }
}
