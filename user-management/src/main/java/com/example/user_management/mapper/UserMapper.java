package com.example.user_management.mapper;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.example.user_management.dao.RoleEntity;
import com.example.user_management.dao.UserEntity;
import com.example.user_management.dto.UserRequestDTO;
import com.example.user_management.dto.UserResponseDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class UserMapper {

    private final ModelMapper modelMapper;

    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UserEntity toEntity(UserRequestDTO userRequestDTO, Set<RoleEntity> roles) {
        log.debug("Mapping UserRequestDTO to UserEntity: {}", userRequestDTO);
        
        // Map DTO to Entity
        UserEntity userEntity = modelMapper.map(userRequestDTO, UserEntity.class);
        log.debug("Mapped UserRequestDTO to UserEntity: {}", userEntity);

        // Set roles for the UserEntity
        if (roles != null && !roles.isEmpty()) {
            userEntity.setRoles(roles);
            log.debug("Roles set for UserEntity: {}", roles);
        } else {
            log.warn("No roles provided for UserEntity.");
        }

        return userEntity;
    }

    public UserResponseDTO toDTO(UserEntity userEntity) {
        log.debug("Mapping UserEntity to UserResponseDTO: {}", userEntity);

        // Map UserEntity to DTO
        UserResponseDTO userResponseDTO = modelMapper.map(userEntity, UserResponseDTO.class);
        log.debug("Mapped UserEntity to UserResponseDTO (basic): {}", userResponseDTO);

        // Map roles (from Set<RoleEntity> to Set<String>)
        if (userEntity.getRoles() != null && !userEntity.getRoles().isEmpty()) {
            Set<String> roleNames = userEntity.getRoles().stream()
                    .map(RoleEntity::getName)
                    .collect(Collectors.toSet());
            userResponseDTO.setRoles(roleNames);
            log.debug("Mapped roles to UserResponseDTO: {}", roleNames);
        } else {
            userResponseDTO.setRoles(Collections.emptySet());
            log.warn("No roles found for UserEntity, setting empty set for UserResponseDTO.");
        }

        // Map status (if available)
        if (userEntity.getStatus() != null) {
            userResponseDTO.setStatus(userEntity.getStatus());
            log.debug("Mapped status to UserResponseDTO: {}", userEntity.getStatus());
        } else {
            log.warn("Status is null in UserEntity, skipping status mapping.");
        }

        return userResponseDTO;
    }
}
