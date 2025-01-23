package com.example.user_management.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.example.user_management.dao.RoleEntity;
import com.example.user_management.dao.UserEntity;
import com.example.user_management.dto.UserRequestDTO;
import com.example.user_management.dto.UserResponseDTO;
import com.example.user_management.enums.Status;
import com.example.user_management.exception.EntityAlreadyExistsException;
import com.example.user_management.exception.EntityNotFoundException;
import com.example.user_management.mapper.UserMapper;
import com.example.user_management.repository.RoleRepository;
import com.example.user_management.repository.UserRepository;
import com.example.user_management.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    
    private final RoleRepository roleRepository;
    
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper 
    		userMapper, RoleRepository roleRepository) {
    	
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        log.info("Creating user with details: {}", userRequestDTO);

        // Check for duplicate username
        if (userRepository.existsByUsername(userRequestDTO.getUsername())) {
            log.error("Username already exists: {}", userRequestDTO.getUsername());
            throw new EntityAlreadyExistsException("Username already exists");
        }

        // Check for duplicate email
        if (userRepository.existsByEmail(userRequestDTO.getEmail())) {
            log.error("Email already exists: {}", userRequestDTO.getEmail());
            throw new EntityAlreadyExistsException("Email already exists");
        }

        try {
            log.info("Assigning default role to the user");
            Set<RoleEntity> roles = new HashSet<>();
            RoleEntity defaultRole = roleRepository.findByName("ROLE_USER")
                    .orElseThrow(() -> {
                        log.error("Default role 'ROLE_USER' not found in the system");
                        return new EntityNotFoundException("Default role 'ROLE_USER' not found");
                    });
            roles.add(defaultRole);

            // Map UserRequestDTO to UserEntity and assign roles
            UserEntity userEntity = userMapper.toEntity(userRequestDTO, roles);
            log.debug("Mapped UserEntity: {}", userEntity);

            // Set default status if not provided
            if (userEntity.getStatus() == null) {
                userEntity.setStatus(Status.ACTIVE);  // Assuming "ACTIVE" as the default status
            }

            // Save UserEntity in the database
            log.debug("Saving UserEntity to database: {}", userEntity);
            UserEntity savedUser = userRepository.save(userEntity);
            log.debug("UserEntity saved successfully with ID: {}", savedUser.getId());

            // Map the saved UserEntity to UserResponseDTO
            UserResponseDTO responseDTO = userMapper.toDTO(savedUser);
            log.info("User created successfully: {}", responseDTO);
            
            // Return the responseDTO with status created
            return responseDTO;

        } catch (EntityAlreadyExistsException | EntityNotFoundException ex) {
            // Rethrow custom exceptions with detailed logs
            log.error("Entity creation failed: {}", ex.getMessage());
            throw ex;  // Rethrow as is, since it is the expected behavior
        } catch (Exception ex) {
            // Handle unexpected errors and log them properly
            log.error("Unexpected error occurred while creating user: {}", ex.getMessage(), ex);
            throw new RuntimeException("An unexpected error occurred while creating the user", ex);
        }
    }

    @Override
    public UserResponseDTO getUserById(Long id) {
        log.info("Fetching user with ID: {}", id);

        try {
            // Fetch the UserEntity by ID
            UserEntity user = userRepository.findById(id)
                    .orElseThrow(() -> {
                        log.warn("User with ID: {} not found", id);
                        return new RuntimeException("User not found");
                    });

            // Map the UserEntity to UserResponseDTO
            UserResponseDTO responseDTO = userMapper.toDTO(user);
            log.info("Fetched user details: {}", responseDTO);
            return responseDTO;

        } catch (Exception e) {
            log.error("Error fetching user with ID {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Error fetching user");
        }
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {
        log.info("Fetching all users");

        try {
            // Fetch all users from the repository
            List<UserEntity> users = userRepository.findAll();
            log.debug("Fetched {} users from the database", users.size());

            // Convert each UserEntity to UserResponseDTO
            List<UserResponseDTO> responseDTOs = users.stream()
                    .map(userMapper::toDTO)
                    .collect(Collectors.toList());

            log.info("Returning {} users as responseDTO", responseDTOs.size());
            return responseDTOs;

        } catch (Exception e) {
            log.error("Error fetching all users: {}", e.getMessage(), e);
            throw new RuntimeException("Error fetching users");
        }
    }

    // Other service methods can be implemented here as needed
}
