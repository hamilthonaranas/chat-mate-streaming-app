package com.chattmate.api.model.entity;

import jakarta.persistence.*;
import com.chattmate.api.model.enum.StreamStatus;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Stream Entity - Represents a live stream
 */
@Entity
@Table(name = "streams", indexes = {
    @Index(name = "idx_broadcaster_id", columnList = "broadcaster_id"),
    @Index(name = "idx_room_name", columnList = "room_name", unique = true),
    @Index(name = "idx_status", columnList = "status")
})
public class Stream {
    
    @Id
    private String id = UUID.randomUUID().toString();
    
    @Column(name = "room_name", unique = true, nullable = false)
    private String roomName;
    
    @Column(name = "broadcaster_id", nullable = false)
    private String broadcasterId;
    
    @Column(name = "broadcaster_name")
    private String broadcasterName;
    
    private String title;
    private String description;
    private String category;
    
    @Enumerated(EnumType.STRING)
    private StreamStatus status = StreamStatus.IDLE;
    
    @Column(name = "viewer_count")
    private int viewerCount = 0;
    
    @Column(name = "started_at")
    private LocalDateTime startedAt;
    
    @Column(name = "ended_at")
    private LocalDateTime endedAt;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();
    
    // Constructors
    public Stream() {}
    
    public Stream(String roomName, String broadcasterId) {
        this.roomName = roomName;
        this.broadcasterId = broadcasterId;
    }
    
    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getRoomName() { return roomName; }
    public void setRoomName(String roomName) { this.roomName = roomName; }
    
    public String getBroadcasterId() { return broadcasterId; }
    public void setBroadcasterId(String broadcasterId) { this.broadcasterId = broadcasterId; }
    
    public String getBroadcasterName() { return broadcasterName; }
    public void setBroadcasterName(String broadcasterName) { this.broadcasterName = broadcasterName; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    
    public StreamStatus getStatus() { return status; }
    public void setStatus(StreamStatus status) { this.status = status; }
    
    public int getViewerCount() { return viewerCount; }
    public void setViewerCount(int viewerCount) { this.viewerCount = viewerCount; }
    
    public LocalDateTime getStartedAt() { return startedAt; }
    public void setStartedAt(LocalDateTime startedAt) { this.startedAt = startedAt; }
    
    public LocalDateTime getEndedAt() { return endedAt; }
    public void setEndedAt(LocalDateTime endedAt) { this.endedAt = endedAt; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
