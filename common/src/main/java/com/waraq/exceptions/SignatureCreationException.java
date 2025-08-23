package com.waraq.exceptions;

public class SignatureCreationException extends RuntimeException {

    public SignatureCreationException() {
    }

    public SignatureCreationException(String message) {
        super(message);
    }

    public SignatureCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
