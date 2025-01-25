package com.example.user_management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponseDTO {

    private String accessToken;

    private String tokenType = "Bearer";

    private Long expiresIn;

    private String refreshToken;
    
    public AuthResponseDTO(String accessToken) {
        this.accessToken = accessToken;
        this.tokenType = "Bearer";
        this.expiresIn = null; // Or provide a default value
        this.refreshToken = null; // Or provide a default value
    }
}
