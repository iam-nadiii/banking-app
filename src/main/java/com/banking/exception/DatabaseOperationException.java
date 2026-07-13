package com.banking.exception;

/**
 * Thrown when a database or external service operation fails unexpectedly.
 * HTTP Status: 500 INTERNAL_SERVER_ERROR
 */

public class DatabaseOperationException extends ApplicationException{

    public DatabaseOperationException(String message) {
        super(message);
    }

    public DatabaseOperationException(String message, Throwable cause) {
        super(message, cause);
    }

}
