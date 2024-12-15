package com.learnbydoing.tradingapp.exceptionHandler;

public class ResourceNotFoundException extends RuntimeException {

    // Constructor that accepts a message
    public ResourceNotFoundException(String message) {
        super(message);
    }

    // Constructor that accepts a message and cause
    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
