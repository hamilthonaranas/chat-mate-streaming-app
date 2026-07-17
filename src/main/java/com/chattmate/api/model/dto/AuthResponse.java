package com.chattmate.api.model.dto;

import jakarta.validation.constraints.NotNull;

/**
 * Authentication Response DTO
 * Returned after successful login/registration
 */
public class AuthResponse {
    
    private String token;
    private String refreshToken;
    private String type = "Bearer";
    private UserDTO user;
    private Long expiresIn;
    
    // Constructors
    public AuthResponse() {}
    
    public AuthResponse(String token, UserDTO user) {
        this.token = token;
        this.user = user;
    }
    
    public AuthResponse(String token, String refreshToken, UserDTO user, Long expiresIn) {
        this.token = token;
        this.refreshToken = refreshToken;
        this.user = user;
        this.expiresIn = expiresIn;
    }
    
    // Getters and Setters
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    
    public String getRefreshToken() { return refreshToken; }
    public void setRefreshToken(String refreshToken) { this.refreshToken = refreshToken; }
    
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    
    public UserDTO getUser() { return user; }
    public void setUser(UserDTO user) { this.user = user; }
    
    public Long getExpiresIn() { return expiresIn; }
    public void setExpiresIn(Long expiresIn) { this.expiresIn = expiresIn; }
}
