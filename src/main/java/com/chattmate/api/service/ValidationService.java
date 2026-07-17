package com.chattmate.api.service;

import com.chattmate.api.model.entity.User;
import com.chattmate.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

/**
 * Validation Service - Business logic for user validation
 * Handles age calculation, eligibility checks, etc.
 */
@Service
@Transactional(readOnly = true)
public class ValidationService {
    
    @Autowired
    private UserRepository userRepository;
    
    private static final int MINIMUM_AGE = 18;
    
    /**
     * Check if user is eligible for premium features
     * Requirements: Adult verified + Has deposited tokens
     */
    public boolean isEligibleForFeatures(User user) {
        return user != null 
            && user.isVerifiedAdult() 
            && user.isHasDeposited();
    }
    
    /**
     * Calculate age from date of birth
     */
    public int calculateAge(LocalDate dateOfBirth) {
        if (dateOfBirth == null) {
            return 0;
        }
        return Period.between(dateOfBirth, LocalDate.now()).getYears();
    }
    
    /**
     * Validate user is 18+ years old
     */
    public boolean isAdult(LocalDate dateOfBirth) {
        int age = calculateAge(dateOfBirth);
        return age >= MINIMUM_AGE;
    }
    
    /**
     * Validate email format
     */
    public boolean isValidEmail(String email) {
        if (email == null || email.isBlank()) {
            return false;
        }
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }
    
    /**
     * Check if username exists
     */
    public boolean usernameExists(String username) {
        return userRepository.findByUsername(username).isPresent();
    }
    
    /**
     * Check if email exists
     */
    public boolean emailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
}
