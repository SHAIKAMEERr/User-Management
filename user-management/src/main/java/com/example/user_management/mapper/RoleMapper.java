package com.example.user_management.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.example.user_management.dao.RoleEntity;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class RoleMapper {

    private final ModelMapper modelMapper;

    public RoleMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public RoleEntity toEntity(String roleName) {
        log.debug("Mapping role name to RoleEntity: {}", roleName);
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setName(roleName);
        return roleEntity;
    }

    public String toDTO(RoleEntity roleEntity) {
        log.debug("Mapping RoleEntity to role name: {}", roleEntity);
        return roleEntity.getName();
    }
}
