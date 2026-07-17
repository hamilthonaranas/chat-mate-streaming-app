package com.chattmate.api.model.enum;

/**
 * Transaction Status Enumeration
 */
public enum TransactionStatus {
    PENDING("Payment Pending"),
    SUCCESS("Payment Successful"),
    FAILED("Payment Failed"),
    CANCELLED("Payment Cancelled"),
    REFUNDED("Payment Refunded");
    
    private final String description;
    
    TransactionStatus(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
}
