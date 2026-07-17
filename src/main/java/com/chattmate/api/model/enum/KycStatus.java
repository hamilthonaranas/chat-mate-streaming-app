package com.chattmate.api.model.enum;

/**
 * KYC (Know Your Customer) Status Enumeration
 * Represents the status of user's KYC verification
 */
public enum KycStatus {
    PENDING("Pending Verification"),
    APPROVED("KYC Verified"),
    REJECTED("KYC Rejected"),
    EXPIRED("Verification Expired");
    
    private final String displayName;
    
    KycStatus(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}
