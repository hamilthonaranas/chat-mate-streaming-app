package com.chattmate.api.model.enum;

/**
 * Stream Status Enumeration
 */
public enum StreamStatus {
    IDLE("Stream Idle"),
    LIVE("Live Streaming"),
    PAUSED("Stream Paused"),
    ENDED("Stream Ended"),
    ERROR("Stream Error");
    
    private final String description;
    
    StreamStatus(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
}
