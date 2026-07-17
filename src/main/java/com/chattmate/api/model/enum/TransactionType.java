package com.chattmate.api.model.enum;

/**
 * Transaction Type Enumeration
 */
public enum TransactionType {
    DEPOSIT("Token Purchase"),
    WITHDRAWAL("Token Withdrawal"),
    TRANSFER("Token Transfer"),
    REFUND("Refund"),
    ADJUSTMENT("Balance Adjustment");
    
    private final String description;
    
    TransactionType(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
}
