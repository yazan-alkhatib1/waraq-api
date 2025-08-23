package com.waraq.exceptions;

public class DocumentUploadException extends RuntimeException {

    public DocumentUploadException(String message) {
        super(message);
    }

    public DocumentUploadException(Throwable cause) {
        super(cause);
    }
}
