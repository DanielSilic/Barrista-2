package com.example.backend.exceptions;

public class KaffeesorteDoesNotExistException extends Exception {

    public KaffeesorteDoesNotExistException(String message) {
        super(message);
    }

    public KaffeesorteDoesNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

}
