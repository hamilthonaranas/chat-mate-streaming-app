package com.chattmate.api.exception;
package com.chattmate.api.exception;

/**
 * Validation Exception
 * Thrown when business rule validation fails
 */
public class ValidationException extends RuntimeException {
    
    public ValidationException(String message) {
        super(message);
    }
    
    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
