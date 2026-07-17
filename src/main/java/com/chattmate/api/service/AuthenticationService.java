package com.chattmate.api.service;

import com.chattmate.api.model.entity.User;
import com.chattmate.api.model.dto.LoginRequest;
import com.chattmate.api.model.dto.RegisterRequest;
import com.chattmate.api.model.dto.AuthResponse;
import com.chattmate.api.repository.UserRepository;
import com.chattmate.api.security.JwtTokenProvider;
import com.chattmate.api.exception.ValidationException;
import com.chattmate.api.exception.UnauthorizedException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Authentication Service
 * Handles user login, registration, and JWT token management
 */
@Service
@Transactional
public class AuthenticationService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ValidationService validationService;
    
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    /**
     * Register new user
     */
    public User register(RegisterRequest request) {
        // Validate age
        if (!validationService.isAdult(request.getDateOfBirth())) {
            throw new ValidationException("You must be at least 18 years old to register");
        }
        
        // Check email not taken
        if (validationService.emailExists(request.getEmail())) {
            throw new ValidationException("Email already registered");
        }
        
        // Check username not taken
        if (validationService.usernameExists(request.getUsername())) {
            throw new ValidationException("Username already taken");
        }
        
        // Validate passwords match
        if (!request.getPassword().equals(request.getPasswordConfirm())) {
            throw new ValidationException("Passwords do not match");
        }
        
        // Create user
        User user = new User();
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        user.setFullName(request.getFullName());
        user.setDateOfBirth(request.getDateOfBirth());
        user.setPlaceOfBirth(request.getPlaceOfBirth());
        user.setCityLocation(request.getCityLocation());
        user.setGoogleId(request.getGoogleId() != null ? request.getGoogleId() : "");
        user.setVerifiedAdult(false);
        user.setHasDeposited(false);
        user.setTokenBalance(0);
        
        return userRepository.save(user);
    }
    
    /**
     * Authenticate and generate JWT token
     */
    public AuthResponse login(LoginRequest request) {
        // Find user
        Optional<User> userOpt = userRepository.findByEmail(request.getEmail());
        if (userOpt.isEmpty()) {
            throw new UnauthorizedException("Invalid email or password");
        }
        
        // Generate token (in production, verify password)
        String token = jwtTokenProvider.generateTokenFromUsername(request.getEmail());
        long expiresIn = jwtTokenProvider.getExpirationTime(token) - System.currentTimeMillis();
        
        AuthResponse response = new AuthResponse();
        response.setToken(token);
        response.setType("Bearer");
        response.setExpiresIn(expiresIn);
        
        return response;
    }
    
    /**
     * Validate JWT token
     */
    public boolean validateToken(String token) {
        return jwtTokenProvider.validateToken(token);
    }
    
    /**
     * Get username from token
     */
    public String getUsernameFromToken(String token) {
        return jwtTokenProvider.getUsernameFromToken(token);
    }
}
