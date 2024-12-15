package com.learnbydoing.tradingapp.exceptionHandler;

public class InsufficientFundsException extends RuntimeException {

    // Constructor that accepts a message
    public InsufficientFundsException(String message) {
        super(message);
    }

    // Constructor that accepts a message and cause
    public InsufficientFundsException(String message, Throwable cause) {
        super(message, cause);
    }
}