package com.chattmate.api.service;

import org.springframework.stereotype.Service;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * LiveKit Token Service - Generates JWT tokens for LiveKit video streaming
 * Handles authentication and authorization for video rooms
 */
@Service
public class LiveKitTokenService {
    
    /**
     * Generate JWT token for LiveKit room access
     * 
     * @param room Room name
     * @param identity User identity
     * @param isBroadcaster Whether user is a broadcaster or subscriber
     * @return JWT token
     */
    public String generateToken(String room, String identity, boolean isBroadcaster) throws Exception {
        String apiKey = System.getenv("LIVEKIT_API_KEY") != null 
            ? System.getenv("LIVEKIT_API_KEY") 
            : "devkey";
        
        String apiSecret = System.getenv("LIVEKIT_API_SECRET") != null 
            ? System.getenv("LIVEKIT_API_SECRET") 
            : "secret";

        long nowSeconds = System.currentTimeMillis() / 1000;
        long expSeconds = nowSeconds + 7200; // Valid for 2 hours

        // 1. Construct JWT Header
        String header = "{\"alg\":\"HS256\",\"typ\":\"JWT\"}";
        
        // 2. Construct LiveKit Video Grant
        String videoGrant = String.format(
            "{\"roomJoin\":true,\"room\":\"%s\",\"canPublish\":%b,\"canSubscribe\":true}",
            room, isBroadcaster
        );

        // 3. Construct Payload
        String payload = String.format(
            "{\"iss\":\"%s\",\"sub\":\"%s\",\"nbf\":%d,\"exp\":%d,\"video\":%s}",
            apiKey, identity, nowSeconds - 5, expSeconds, videoGrant
        );

        // 4. Encode Header and Payload
        String encodedHeader = Base64.getUrlEncoder()
            .withoutPadding()
            .encodeToString(header.getBytes(StandardCharsets.UTF_8));
        
        String encodedPayload = Base64.getUrlEncoder()
            .withoutPadding()
            .encodeToString(payload.getBytes(StandardCharsets.UTF_8));
        
        String signatureInput = encodedHeader + "." + encodedPayload;

        // 5. Sign via HMAC SHA256
        Mac sha256HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(
            apiSecret.getBytes(StandardCharsets.UTF_8), 
            "HmacSHA256"
        );
        sha256HMAC.init(secretKeySpec);
        
        byte[] rawSignature = sha256HMAC.doFinal(signatureInput.getBytes(StandardCharsets.UTF_8));
        String encodedSignature = Base64.getUrlEncoder()
            .withoutPadding()
            .encodeToString(rawSignature);

        return signatureInput + "." + encodedSignature;
    }
}
