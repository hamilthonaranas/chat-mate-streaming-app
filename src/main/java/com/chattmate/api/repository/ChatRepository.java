package com.chattmate.api.repository;

import com.chattmate.api.model.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Chat Repository - Data access for chat messages
 */
@Repository
public interface ChatRepository extends JpaRepository<Chat, String> {
    
    List<Chat> findByRoomId(String roomId);
    
    List<Chat> findByRoomIdOrderByCreatedAtDesc(String roomId);
    
    List<Chat> findBySenderId(String senderId);
    
    @Query("SELECT c FROM Chat c WHERE c.roomId = :roomId ORDER BY c.createdAt DESC LIMIT :limit")
    List<Chat> findLatestMessagesInRoom(@Param("roomId") String roomId, @Param("limit") int limit);
    
    long countByRoomId(String roomId);
}
