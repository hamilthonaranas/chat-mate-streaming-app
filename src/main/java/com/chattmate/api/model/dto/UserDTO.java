package com.chattmate.api.model.dto;

import com.chattmate.api.model.enum.KycStatus;
import java.time.LocalDate;

/**
 * User Data Transfer Object (DTO)
 * Used for API responses - doesn't expose sensitive data
 */
public class UserDTO {
    private String id;
    private String username;
    private String email;
    private String fullName;
    private LocalDate dateOfBirth;
    private String cityLocation;
    private String interestedIn;
    private boolean smoke;
    private boolean drink;
    private int tokenBalance;
    private boolean isVerifiedAdult;
    private KycStatus kycStatus;
    
    // Constructors
    public UserDTO() {}
    
    public UserDTO(String id, String username, String email, String fullName) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.fullName = fullName;
    }
    
    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    
    public String getCityLocation() { return cityLocation; }
    public void setCityLocation(String cityLocation) { this.cityLocation = cityLocation; }
    
    public String getInterestedIn() { return interestedIn; }
    public void setInterestedIn(String interestedIn) { this.interestedIn = interestedIn; }
    
    public boolean isSmoke() { return smoke; }
    public void setSmoke(boolean smoke) { this.smoke = smoke; }
    
    public boolean isDrink() { return drink; }
    public void setDrink(boolean drink) { this.drink = drink; }
    
    public int getTokenBalance() { return tokenBalance; }
    public void setTokenBalance(int tokenBalance) { this.tokenBalance = tokenBalance; }
    
    public boolean isVerifiedAdult() { return isVerifiedAdult; }
    public void setVerifiedAdult(boolean verifiedAdult) { isVerifiedAdult = verifiedAdult; }
    
    public KycStatus getKycStatus() { return kycStatus; }
    public void setKycStatus(KycStatus kycStatus) { this.kycStatus = kycStatus; }
}
