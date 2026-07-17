package com.chattmate.api.exception;

/**
 * Resource Not Found Exception
 * Thrown when a requested resource is not found in the database
 */
public class ResourceNotFoundException extends RuntimeException {
    
    public ResourceNotFoundException(String message) {
        super(message);
    }
    
    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
