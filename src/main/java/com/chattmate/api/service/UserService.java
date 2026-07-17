package com.chattmate.api.service;

import com.chattmate.api.model.entity.User;
import com.chattmate.api.repository.UserRepository;
import com.chattmate.api.exception.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * User Service
 * Handles user profile management and operations
 */
@Service
@Transactional
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    /**
     * Get user by ID
     */
    public User getUserById(String userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));
    }
    
    /**
     * Get user by email
     */
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    /**
     * Get user by username
     */
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    /**
     * Update user profile
     */
    public User updateUserProfile(String userId, User updatedUser) {
        User user = getUserById(userId);
        
        if (updatedUser.getCityLocation() != null) {
            user.setCityLocation(updatedUser.getCityLocation());
        }
        if (updatedUser.getInterestedIn() != null) {
            user.setInterestedIn(updatedUser.getInterestedIn());
        }
        
        user.setShowFullName(updatedUser.isShowFullName());
        user.setShowAge(updatedUser.isShowAge());
        user.setSmoke(updatedUser.isSmoke());
        user.setDrink(updatedUser.isDrink());
        user.setUpdatedAt(LocalDateTime.now());
        
        return userRepository.save(user);
    }
    
    /**
     * Delete user account
     */
    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }
    
    /**
     * Check if user is verified adult
     */
    public boolean isVerifiedAdult(String userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        return userOpt.map(User::isVerifiedAdult).orElse(false);
    }
    
    /**
     * Check if user has deposited
     */
    public boolean hasDeposited(String userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        return userOpt.map(User::isHasDeposited).orElse(false);
    }
    
    /**
     * Get user token balance
     */
    public int getTokenBalance(String userId) {
        User user = getUserById(userId);
        return user.getTokenBalance();
    }
}
