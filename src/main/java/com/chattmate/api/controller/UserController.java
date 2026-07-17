package com.chattmate.api.controller;

import com.chattmate.api.model.entity.User;
import com.chattmate.api.model.dto.UserDTO;
import com.chattmate.api.repository.UserRepository;
import com.chattmate.api.service.ValidationService;
import com.chattmate.api.exception.ResourceNotFoundException;
import com.chattmate.api.exception.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * User Controller - Handles user-related HTTP endpoints
 * Responsible for:
 * - User registration
 * - Profile management
 * - User information retrieval
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ValidationService validationService;

    /**
     * Register a new user
     * POST /api/users/register
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User registrationData) {
        try {
            // Validate age
            int calculatedAge = validationService.calculateAge(registrationData.getDateOfBirth());
            if (calculatedAge < 18) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error", "You must be at least 18 years old to register."));
            }

            // Check email doesn't exist
            if (validationService.emailExists(registrationData.getEmail())) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("error", "Email already registered."));
            }

            // Check username doesn't exist
            if (validationService.usernameExists(registrationData.getUsername())) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("error", "Username already taken."));
            }

            registrationData.setVerifiedAdult(false);
            User savedUser = userRepository.save(registrationData);
            
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(convertToDTO(savedUser));
                
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Registration failed: " + e.getMessage()));
        }
    }

    /**
     * Get user profile by ID
     * GET /api/users/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserProfile(@PathVariable String id) {
        Optional<User> userOpt = userRepository.findById(id);
        
        if (userOpt.isEmpty()) {
            throw new ResourceNotFoundException("User not found with ID: " + id);
        }
        
        return ResponseEntity.ok(convertToDTO(userOpt.get()));
    }

    /**
     * Update user profile
     * PUT /api/users/{id}/profile
     */
    @PutMapping("/{id}/profile")
    public ResponseEntity<?> updateProfile(@PathVariable String id, @RequestBody User profileUpdates) {
        Optional<User> userOpt = userRepository.findById(id);
        
        if (userOpt.isEmpty()) {
            throw new ResourceNotFoundException("User not found with ID: " + id);
        }

        User user = userOpt.get();

        // Check eligibility
        if (!validationService.isEligibleForFeatures(user)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(Map.of("error", "Verification and deposit required to update profile."));
        }

        // Apply updates
        if (profileUpdates.getCityLocation() != null) {
            user.setCityLocation(profileUpdates.getCityLocation());
        }
        if (profileUpdates.getInterestedIn() != null) {
            user.setInterestedIn(profileUpdates.getInterestedIn());
        }
        user.setShowFullName(profileUpdates.isShowFullName());
        user.setShowAge(profileUpdates.isShowAge());
        user.setSmoke(profileUpdates.isSmoke());
        user.setDrink(profileUpdates.isDrink());

        User updated = userRepository.save(user);
        return ResponseEntity.ok(convertToDTO(updated));
    }

    /**
     * Convert User entity to UserDTO
     */
    private UserDTO convertToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setFullName(user.getFullName());
        dto.setDateOfBirth(user.getDateOfBirth());
        dto.setCityLocation(user.getCityLocation());
        dto.setInterestedIn(user.getInterestedIn());
        dto.setSmoke(user.isSmoke());
        dto.setDrink(user.isDrink());
        dto.setTokenBalance(user.getTokenBalance());
        dto.setVerifiedAdult(user.isVerifiedAdult());
        dto.setKycStatus(user.getKycStatus());
        return dto;
    }
}
