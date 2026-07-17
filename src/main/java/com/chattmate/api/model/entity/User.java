package com.chattmate.api.model.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * User Entity - Represents a ChattMate user
 * Maps to 'users' table in database
 */
@Entity
@Table(name = "users", indexes = {
    @Index(name = "idx_email", columnList = "email", unique = true),
    @Index(name = "idx_google_id", columnList = "google_id", unique = true),
    @Index(name = "idx_username", columnList = "username")
})
public class User {
    
    @Id
    private String id = UUID.randomUUID().toString();
    
    @Column(unique = true, nullable = false)
    private String email;
    
    @Column(name = "google_id", unique = true, nullable = false)
    private String googleId;
    
    @Column(nullable = false)
    private String username;
    
    @Column(nullable = false)
    private String fullName;
    
    @Column(nullable = false)
    private LocalDate dateOfBirth;
    
    private String placeOfBirth;
    
    @Column(name = "city_location")
    private String cityLocation;
    
    // Privacy Settings
    @Column(name = "show_full_name")
    private boolean showFullName = false;
    
    @Column(name = "show_age")
    private boolean showAge = false;
    
    @Column(name = "interested_in")
    private String interestedIn;
    
    private boolean smoke = false;
    private boolean drink = false;
    
    // Verification Flags
    @Column(name = "is_verified_adult")
    private boolean isVerifiedAdult = false;
    
    @Column(name = "has_deposited")
    private boolean hasDeposited = false;
    
    @Column(name = "token_balance")
    private int tokenBalance = 0;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "kyc_status")
    private KycStatus kycStatus = KycStatus.PENDING;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();
    
    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getGoogleId() { return googleId; }
    public void setGoogleId(String googleId) { this.googleId = googleId; }
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    
    public String getPlaceOfBirth() { return placeOfBirth; }
    public void setPlaceOfBirth(String placeOfBirth) { this.placeOfBirth = placeOfBirth; }
    
    public String getCityLocation() { return cityLocation; }
    public void setCityLocation(String cityLocation) { this.cityLocation = cityLocation; }
    
    public boolean isShowFullName() { return showFullName; }
    public void setShowFullName(boolean showFullName) { this.showFullName = showFullName; }
    
    public boolean isShowAge() { return showAge; }
    public void setShowAge(boolean showAge) { this.showAge = showAge; }
    
    public String getInterestedIn() { return interestedIn; }
    public void setInterestedIn(String interestedIn) { this.interestedIn = interestedIn; }
    
    public boolean isSmoke() { return smoke; }
    public void setSmoke(boolean smoke) { this.smoke = smoke; }
    
    public boolean isDrink() { return drink; }
    public void setDrink(boolean drink) { this.drink = drink; }
    
    public boolean isVerifiedAdult() { return isVerifiedAdult; }
    public void setVerifiedAdult(boolean verifiedAdult) { isVerifiedAdult = verifiedAdult; }
    
    public boolean isHasDeposited() { return hasDeposited; }
    public void setHasDeposited(boolean hasDeposited) { this.hasDeposited = hasDeposited; }
    
    public int getTokenBalance() { return tokenBalance; }
    public void setTokenBalance(int tokenBalance) { this.tokenBalance = tokenBalance; }
    
    public KycStatus getKycStatus() { return kycStatus; }
    public void setKycStatus(KycStatus kycStatus) { this.kycStatus = kycStatus; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
