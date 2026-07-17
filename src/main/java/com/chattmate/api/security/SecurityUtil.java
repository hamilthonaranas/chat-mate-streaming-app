package com.chattmate.api.security;

/**
 * Security Utility Class
 * Helper methods for security operations
 */
public class SecurityUtil {
    
    /**
     * Extract token from Authorization header
     */
    public static String extractTokenFromHeader(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }
    
    /**
     * Create Authorization header with token
     */
    public static String createAuthorizationHeader(String token) {
        return "Bearer " + token;
    }
    
    /**
     * Check if header contains valid bearer token
     */
    public static boolean isValidBearerToken(String authorizationHeader) {
        return authorizationHeader != null && authorizationHeader.startsWith("Bearer ") && authorizationHeader.length() > 7;
    }
}
