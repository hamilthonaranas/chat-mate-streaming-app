package com.chattmate.api.model.dto;

import java.time.LocalDateTime;

/**
 * Transaction DTO
 * Represents a payment transaction
 */
public class TransactionDTO {
    
    private String id;
    private String userId;
    private String type; // DEPOSIT, WITHDRAWAL, TRANSFER
    private Integer amount;
    private String status; // PENDING, SUCCESS, FAILED
    private LocalDateTime createdAt;
    private String description;
    private String referenceId;
    
    // Constructors
    public TransactionDTO() {}
    
    public TransactionDTO(String id, String userId, String type, Integer amount) {
        this.id = id;
        this.userId = userId;
        this.type = type;
        this.amount = amount;
        this.createdAt = LocalDateTime.now();
    }
    
    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    
    public Integer getAmount() { return amount; }
    public void setAmount(Integer amount) { this.amount = amount; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public String getReferenceId() { return referenceId; }
    public void setReferenceId(String referenceId) { this.referenceId = referenceId; }
}
