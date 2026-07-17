package com.chattmate.api.repository;

import com.chattmate.api.model.entity.Stream;
import com.chattmate.api.model.enum.StreamStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Stream Repository - Data access for live streams
 */
@Repository
public interface StreamRepository extends JpaRepository<Stream, String> {
    
    Optional<Stream> findByRoomName(String roomName);
    
    List<Stream> findByBroadcasterId(String broadcasterId);
    
    List<Stream> findByStatus(StreamStatus status);
    
    @Query("SELECT s FROM Stream s WHERE s.status = 'LIVE' ORDER BY s.viewerCount DESC")
    List<Stream> findLiveStreamsOrderByViewers();
    
    @Query("SELECT s FROM Stream s WHERE s.status = 'LIVE' AND s.category = :category")
    List<Stream> findLiveStreamsByCategory(@Param("category") String category);
    
    long countByStatus(StreamStatus status);
}
