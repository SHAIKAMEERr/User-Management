package com.example.user_management.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.user_management.dto.AuthRequestDTO;
import com.example.user_management.dto.AuthResponseDTO;
import com.example.user_management.service.AuthService;

@RestController
@RequestMapping("/api/v1/auth")
@Slf4j
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody
    		AuthRequestDTO authRequestDTO) {
    	
        log.info("Login request received for email: {}", authRequestDTO.getEmail());
        
        AuthResponseDTO authResponse = authService.login(authRequestDTO);
        
        log.info("Login successful for email: {}", authRequestDTO.getEmail());
        
        return ResponseEntity.ok(authResponse);
    }
}
