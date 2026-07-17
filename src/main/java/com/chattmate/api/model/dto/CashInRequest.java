package com.chattmate.api.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * Cash In Request DTO
 * Used for token purchase transactions
 */
public class CashInRequest {
    
    @NotNull(message = "User ID is required")
    private String userId;
    
    @Positive(message = "Token amount must be positive")
    @NotNull(message = "Token amount is required")
    private Integer tokens;
    
    private String paymentMethod; // CARD, WALLET, etc.
    
    // Constructors
    public CashInRequest() {}
    
    public CashInRequest(String userId, Integer tokens) {
        this.userId = userId;
        this.tokens = tokens;
    }
    
    // Getters and Setters
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    
    public Integer getTokens() { return tokens; }
    public void setTokens(Integer tokens) { this.tokens = tokens; }
    
    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
}
