package com.waraq.exceptions;

public class WaraqException extends RuntimeException {
    public WaraqException(String message, Throwable cause) {
        super(message, cause);
    }

    public WaraqException(Throwable cause) {
        super(cause);
    }

    public WaraqException(String message) {
        super(message);
    }
}
