package com.example.user_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.user_management.dao.RoleEntity;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    /**
     * Find a role by name.
     * 
     * @param name the role name to search for
     * @return an Optional containing the RoleEntity if found, else empty
     */
    Optional<RoleEntity> findByName(String name);

    /**
     * Check if a role exists by name.
     * 
     * @param name the role name to check
     * @return true if the role exists, false otherwise
     */
    boolean existsByName(String name);
}
