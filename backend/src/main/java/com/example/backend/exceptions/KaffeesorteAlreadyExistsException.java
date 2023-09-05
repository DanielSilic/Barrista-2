package com.example.backend.exceptions;

public class KaffeesorteAlreadyExistsException extends Exception{

    public KaffeesorteAlreadyExistsException(String message) {
        super(message);
    }

    public KaffeesorteAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
