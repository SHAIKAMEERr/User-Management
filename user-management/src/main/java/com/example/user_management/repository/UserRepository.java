package com.example.user_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.user_management.dao.UserEntity;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    /**
     * Find a user by email.
     * 
     * @param email the email to search for
     * @return an Optional containing the UserEntity if found, else empty
     */
    Optional<UserEntity> findByEmail(String email);

    /**
     * Check if a user exists by email.
     * 
     * @param email the email to check
     * @return true if a user exists, false otherwise
     */
    boolean existsByEmail(String email);
    
    boolean existsByUsername(String username);
}

