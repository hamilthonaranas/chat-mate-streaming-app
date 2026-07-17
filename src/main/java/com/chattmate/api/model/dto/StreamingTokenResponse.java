package com.chattmate.api.model.dto;

/**
 * Streaming Token Response DTO
 * Contains the token for LiveKit access
 */
public class StreamingTokenResponse {
    
    private String token;
    private String roomName;
    private String participantName;
    private Long expiresIn;
    
    // Constructors
    public StreamingTokenResponse() {}
    
    public StreamingTokenResponse(String token, String roomName, String participantName) {
        this.token = token;
        this.roomName = roomName;
        this.participantName = participantName;
        this.expiresIn = 7200000L; // 2 hours in milliseconds
    }
    
    // Getters and Setters
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    
    public String getRoomName() { return roomName; }
    public void setRoomName(String roomName) { this.roomName = roomName; }
    
    public String getParticipantName() { return participantName; }
    public void setParticipantName(String participantName) { this.participantName = participantName; }
    
    public Long getExpiresIn() { return expiresIn; }
    public void setExpiresIn(Long expiresIn) { this.expiresIn = expiresIn; }
}
