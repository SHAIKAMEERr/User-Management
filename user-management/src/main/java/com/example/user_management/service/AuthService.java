package com.example.user_management.service;

import com.example.user_management.dto.AuthRequestDTO;
import com.example.user_management.dto.AuthResponseDTO;

public interface AuthService {
	
    AuthResponseDTO login(AuthRequestDTO authRequestDTO);
    
}

