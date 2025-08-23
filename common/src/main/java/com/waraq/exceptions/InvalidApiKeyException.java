package com.waraq.exceptions;

public class InvalidApiKeyException extends RuntimeException {

    public InvalidApiKeyException() {
    }

    public InvalidApiKeyException(String message) {
        super(message);
    }

    public InvalidApiKeyException(String message, Throwable cause) {
        super(message, cause);
    }
}
