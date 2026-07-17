package com.chattmate.api.service;

import com.chattmate.api.model.entity.Stream;
import com.chattmate.api.model.enum.StreamStatus;
import com.chattmate.api.repository.StreamRepository;
import com.chattmate.api.exception.ResourceNotFoundException;
import com.chattmate.api.exception.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Streaming Service
 * Manages live streams and room management
 */
@Service
@Transactional
public class StreamingService {
    
    @Autowired
    private StreamRepository streamRepository;
    
    @Autowired
    private LiveKitTokenService liveKitTokenService;
    
    /**
     * Create a new live stream room
     */
    public Stream createRoom(String broadcasterId, String broadcasterName, String title) {
        // Generate unique room name
        String roomName = "room_" + broadcasterId + "_" + UUID.randomUUID().toString().substring(0, 8);
        
        // Check room name not duplicate
        if (streamRepository.findByRoomName(roomName).isPresent()) {
            throw new ValidationException("Room name already exists");
        }
        
        Stream stream = new Stream(roomName, broadcasterId);
        stream.setBroadcasterName(broadcasterName);
        stream.setTitle(title != null ? title : "Live Stream");
        stream.setStatus(StreamStatus.IDLE);
        stream.setViewerCount(0);
        
        return streamRepository.save(stream);
    }
    
    /**
     * Start a live stream
     */
    public Stream startStream(String streamId) {
        Optional<Stream> streamOpt = streamRepository.findById(streamId);
        if (streamOpt.isEmpty()) {
            throw new ResourceNotFoundException("Stream not found");
        }
        
        Stream stream = streamOpt.get();
        stream.setStatus(StreamStatus.LIVE);
        stream.setStartedAt(LocalDateTime.now());
        
        return streamRepository.save(stream);
    }
    
    /**
     * End a live stream
     */
    public Stream endStream(String streamId) {
        Optional<Stream> streamOpt = streamRepository.findById(streamId);
        if (streamOpt.isEmpty()) {
            throw new ResourceNotFoundException("Stream not found");
        }
        
        Stream stream = streamOpt.get();
        stream.setStatus(StreamStatus.ENDED);
        stream.setEndedAt(LocalDateTime.now());
        
        return streamRepository.save(stream);
    }
    
    /**
     * Get stream details
     */
    public Stream getStreamDetails(String streamId) {
        return streamRepository.findById(streamId)
            .orElseThrow(() -> new ResourceNotFoundException("Stream not found"));
    }
    
    /**
     * Get stream by room name
     */
    public Optional<Stream> getStreamByRoomName(String roomName) {
        return streamRepository.findByRoomName(roomName);
    }
    
    /**
     * Update viewer count
     */
    public void updateViewerCount(String streamId, int viewerCount) {
        Optional<Stream> streamOpt = streamRepository.findById(streamId);
        if (streamOpt.isPresent()) {
            Stream stream = streamOpt.get();
            stream.setViewerCount(viewerCount);
            streamRepository.save(stream);
        }
    }
    
    /**
     * Get all live streams
     */
    public List<Stream> getLiveStreams() {
        return streamRepository.findByStatus(StreamStatus.LIVE);
    }
    
    /**
     * Get live streams ordered by viewer count
     */
    public List<Stream> getPopularStreams() {
        return streamRepository.findLiveStreamsOrderByViewers();
    }
    
    /**
     * Get broadcaster's streams
     */
    public List<Stream> getBroadcasterStreams(String broadcasterId) {
        return streamRepository.findByBroadcasterId(broadcasterId);
    }
    
    /**
     * Get LiveKit token for stream access
     */
    public String generateStreamToken(String roomName, String userId, boolean isBroadcaster) throws Exception {
        return liveKitTokenService.generateToken(roomName, userId, isBroadcaster);
    }
}
