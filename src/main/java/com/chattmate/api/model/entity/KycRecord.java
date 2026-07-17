package com.chattmate.api.model.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * KYC Record Entity - Stores KYC verification data
 */
@Entity
@Table(name = "kyc_records", indexes = {
    @Index(name = "idx_user_id", columnList = "user_id"),
    @Index(name = "idx_status", columnList = "status")
})
public class KycRecord {
    
    @Id
    private String id = UUID.randomUUID().toString();
    
    @Column(name = "user_id", nullable = false, unique = true)
    private String userId;
    
    private String status = "PENDING"; // PENDING, APPROVED, REJECTED, EXPIRED
    
    @Column(name = "first_name")
    private String firstName;
    
    @Column(name = "last_name")
    private String lastName;
    
    @Column(name = "date_of_birth")
    private String dateOfBirth;
    
    @Column(name = "id_type")
    private String idType; // PASSPORT, DRIVER_LICENSE, etc.
    
    @Column(name = "id_number")
    private String idNumber;
    
    @Column(name = "id_expiry")
    private String idExpiry;
    
    private String country;
    private String state;
    private String address;
    
    @Column(name = "verification_provider")
    private String verificationProvider; // Sumsub, Smile Identity, etc.
    
    @Column(name = "provider_reference_id")
    private String providerReferenceId;
    
    @Column(name = "verified_at")
    private LocalDateTime verifiedAt;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();
    
    private String rejectionReason;
    
    // Constructors
    public KycRecord() {}
    
    public KycRecord(String userId) {
        this.userId = userId;
    }
    
    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    
    public String getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(String dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    
    public String getIdType() { return idType; }
    public void setIdType(String idType) { this.idType = idType; }
    
    public String getIdNumber() { return idNumber; }
    public void setIdNumber(String idNumber) { this.idNumber = idNumber; }
    
    public String getIdExpiry() { return idExpiry; }
    public void setIdExpiry(String idExpiry) { this.idExpiry = idExpiry; }
    
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
    
    public String getState() { return state; }
    public void setState(String state) { this.state = state; }
    
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    
    public String getVerificationProvider() { return verificationProvider; }
    public void setVerificationProvider(String verificationProvider) { this.verificationProvider = verificationProvider; }
    
    public String getProviderReferenceId() { return providerReferenceId; }
    public void setProviderReferenceId(String providerReferenceId) { this.providerReferenceId = providerReferenceId; }
    
    public LocalDateTime getVerifiedAt() { return verifiedAt; }
    public void setVerifiedAt(LocalDateTime verifiedAt) { this.verifiedAt = verifiedAt; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    
    public String getRejectionReason() { return rejectionReason; }
    public void setRejectionReason(String rejectionReason) { this.rejectionReason = rejectionReason; }
}
