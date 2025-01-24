package com.example.user_management.service;

import java.util.List;

import com.example.user_management.dto.UserRequestDTO;
import com.example.user_management.dto.UserResponseDTO;

public interface UserService {
	
    UserResponseDTO createUser(UserRequestDTO userRequestDTO);
    
    UserResponseDTO getUserById(Long id);
    
    List<UserResponseDTO> getAllUsers();
}

