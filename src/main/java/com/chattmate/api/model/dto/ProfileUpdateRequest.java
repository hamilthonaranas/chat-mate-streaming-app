package com.chattmate.api.model.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Profile Update Request DTO
 * Used to update user profile information
 */
public class ProfileUpdateRequest {
    
    private String cityLocation;
    private String interestedIn;
    private boolean smoke;
    private boolean drink;
    private boolean showFullName;
    private boolean showAge;
    
    // Constructors
    public ProfileUpdateRequest() {}
    
    public ProfileUpdateRequest(String cityLocation, String interestedIn) {
        this.cityLocation = cityLocation;
        this.interestedIn = interestedIn;
    }
    
    // Getters and Setters
    public String getCityLocation() { return cityLocation; }
    public void setCityLocation(String cityLocation) { this.cityLocation = cityLocation; }
    
    public String getInterestedIn() { return interestedIn; }
    public void setInterestedIn(String interestedIn) { this.interestedIn = interestedIn; }
    
    public boolean isSmoke() { return smoke; }
    public void setSmoke(boolean smoke) { this.smoke = smoke; }
    
    public boolean isDrink() { return drink; }
    public void setDrink(boolean drink) { this.drink = drink; }
    
    public boolean isShowFullName() { return showFullName; }
    public void setShowFullName(boolean showFullName) { this.showFullName = showFullName; }
    
    public boolean isShowAge() { return showAge; }
    public void setShowAge(boolean showAge) { this.showAge = showAge; }
}
