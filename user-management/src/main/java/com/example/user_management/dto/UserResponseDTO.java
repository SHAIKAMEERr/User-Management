package com.example.user_management.dto;

import java.util.Set;

import com.example.user_management.enums.Status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {

    private Long id;

    private String username;

    private String email;

    private Set<String> roles;
    
    private Status status;
    
    private String message;
    
    public UserResponseDTO(String message) {
        this.message = message;
    }
}
