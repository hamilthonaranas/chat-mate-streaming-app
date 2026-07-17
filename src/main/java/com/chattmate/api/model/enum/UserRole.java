package com.chattmate.api.model.enum;

/**
 * User Role Enumeration
 */
public enum UserRole {
    USER("Regular User"),
    ADMIN("Administrator"),
    MODERATOR("Moderator"),
    VERIFIED("Verified User"),
    SUSPENDED("Suspended User");
    
    private final String description;
    
    UserRole(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
}
