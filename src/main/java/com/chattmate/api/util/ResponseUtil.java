package com.chattmate.api.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Response Utility - Helper for building consistent API responses
 */
public class ResponseUtil {
    
    /**
     * Build success response
     */
    public static Map<String, Object> success(String message, Object data) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", message);
        response.put("data", data);
        return response;
    }
    
    /**
     * Build success response without data
     */
    public static Map<String, Object> success(String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", message);
        return response;
    }
    
    /**
     * Build error response
     */
    public static Map<String, Object> error(String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("error", message);
        return response;
    }
}
