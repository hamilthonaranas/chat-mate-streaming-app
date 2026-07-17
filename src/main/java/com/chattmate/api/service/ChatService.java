package com.chattmate.api.service;

import com.chattmate.api.model.entity.Chat;
import com.chattmate.api.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Chat Service
 * Handles chat messaging and message history
 */
@Service
@Transactional
public class ChatService {
    
    @Autowired
    private ChatRepository chatRepository;
    
    /**
     * Save a chat message
     */
    public Chat saveMessage(Chat message) {
        if (message.getCreatedAt() == null) {
            message.setCreatedAt(LocalDateTime.now());
        }
        if (message.getUpdatedAt() == null) {
            message.setUpdatedAt(LocalDateTime.now());
        }
        
        return chatRepository.save(message);
    }
    
    /**
     * Get all messages in a room
     */
    public List<Chat> getMessagesInRoom(String roomId) {
        return chatRepository.findByRoomIdOrderByCreatedAtDesc(roomId);
    }
    
    /**
     * Get latest N messages in a room
     */
    public List<Chat> getLatestMessagesInRoom(String roomId, int limit) {
        return chatRepository.findLatestMessagesInRoom(roomId, limit);
    }
    
    /**
     * Get a specific message
     */
    public Optional<Chat> getMessage(String messageId) {
        return chatRepository.findById(messageId);
    }
    
    /**
     * Update a message
     */
    public Chat updateMessage(String messageId, String newContent) {
        Optional<Chat> chatOpt = chatRepository.findById(messageId);
        if (chatOpt.isPresent()) {
            Chat chat = chatOpt.get();
            chat.setContent(newContent);
            chat.setEdited(true);
            chat.setUpdatedAt(LocalDateTime.now());
            return chatRepository.save(chat);
        }
        return null;
    }
    
    /**
     * Delete a message
     */
    public void deleteMessage(String messageId) {
        chatRepository.deleteById(messageId);
    }
    
    /**
     * Get message count for room
     */
    public long getMessageCount(String roomId) {
        return chatRepository.countByRoomId(roomId);
    }
    
    /**
     * Get all messages from a user
     */
    public List<Chat> getUserMessages(String userId) {
        return chatRepository.findBySenderId(userId);
    }
}
