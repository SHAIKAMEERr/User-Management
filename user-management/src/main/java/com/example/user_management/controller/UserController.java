package com.example.user_management.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.user_management.dto.UserRequestDTO;
import com.example.user_management.dto.UserResponseDTO;
import com.example.user_management.exception.EntityAlreadyExistsException;
import com.example.user_management.exception.ValidationException;
import com.example.user_management.service.UserService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/users")
@Slf4j
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        log.info("Received request to create user: {}", userRequestDTO);

        try {
            // Call service to create user
            UserResponseDTO createdUser = userService.createUser(userRequestDTO);

            log.info("User created successfully: {}", createdUser);

            // Return success response
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (EntityAlreadyExistsException e) {
            log.error("User creation failed, user already exists: {}", userRequestDTO.getUsername(), e);
            // Return conflict status in case of a duplicate user (username or email)
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new UserResponseDTO(e.getMessage())); // Now this works with the added constructor
        } catch (ValidationException e) {
            log.error("Invalid user data: {}", userRequestDTO, e);
            // Return bad request status for invalid input
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            log.error("An unexpected error occurred while creating user: {}", userRequestDTO, e);
            // General error handling
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
    	
        log.info("Fetching user with ID: {}", id);
        
        UserResponseDTO user = userService.getUserById(id);
        
        log.info("User fetched successfully: {}", user);
        
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
    	
        log.info("Fetching all users");
        
        List<UserResponseDTO> users = userService.getAllUsers();
        
        log.info("Users fetched successfully: {}", users);
        
        return ResponseEntity.ok(users);
    }
}
