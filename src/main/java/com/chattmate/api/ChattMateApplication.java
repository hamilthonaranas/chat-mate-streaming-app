package com.chattmate.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Main Spring Boot application entry point for ChattMate Streaming Chat Application
 * 
 * Features:
 * - RESTful API endpoints
 * - JWT Authentication
 * - KYC Verification
 * - Payment Processing
 * - LiveKit Video Streaming
 * - WebSocket Real-time Chat
 * - User Profile Management
 */
@SpringBootApplication
@EnableAsync
@EnableScheduling
@EnableJpaAuditing
public class ChattMateApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChattMateApplication.class, args);
    }
}
