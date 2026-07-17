package com.chattmate.api.model.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Streaming Token Request DTO
 * Used to request LiveKit tokens for video streaming
 */
public class StreamingTokenRequest {
    
    @NotBlank(message = "Room name is required")
    private String room;
    
    @NotBlank(message = "User identity is required")
    private String identity;
    
    private boolean isBroadcaster = false;
    
    // Constructors
    public StreamingTokenRequest() {}
    
    public StreamingTokenRequest(String room, String identity, boolean isBroadcaster) {
        this.room = room;
        this.identity = identity;
        this.isBroadcaster = isBroadcaster;
    }
    
    // Getters and Setters
    public String getRoom() { return room; }
    public void setRoom(String room) { this.room = room; }
    
    public String getIdentity() { return identity; }
    public void setIdentity(String identity) { this.identity = identity; }
    
    public boolean isBroadcaster() { return isBroadcaster; }
    public void setBroadcaster(boolean broadcaster) { isBroadcaster = broadcaster; }
}
