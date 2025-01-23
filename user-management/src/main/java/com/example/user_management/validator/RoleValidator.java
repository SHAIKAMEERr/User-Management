package com.example.user_management.validator;

import org.springframework.stereotype.Component;
import com.example.user_management.enums.UserRole;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class RoleValidator {

    public void validateRole(String role) {
    	
        log.info("Validating role: {}", role);

        try {
            UserRole.valueOf(role.toUpperCase());
            
        } catch (IllegalArgumentException e) {
        	
            log.error("Invalid role: {}", role);
            
            throw new IllegalArgumentException("Role must be one of:"
            		+ " ADMIN, USER, MODERATOR");
        }

        log.info("Role validated successfully");
    }
}
