package com.chattmate.api.repository;

import com.chattmate.api.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

/**
 * User Repository - Data access layer for User entity
 * Provides CRUD operations and custom queries
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {
    
    Optional<User> findByEmail(String email);
    
    Optional<User> findByGoogleId(String googleId);
    
    Optional<User> findByUsername(String username);
    
    @Query("SELECT u FROM User u WHERE u.isVerifiedAdult = true AND u.hasDeposited = true")
    List<User> findVerifiedAndActiveUsers();
    
    @Query("SELECT COUNT(u) FROM User u WHERE u.isVerifiedAdult = true")
    long countVerifiedUsers();
    
    @Query("SELECT u FROM User u WHERE u.kycStatus = 'PENDING'")
    List<User> findPendingKycUsers();
}
