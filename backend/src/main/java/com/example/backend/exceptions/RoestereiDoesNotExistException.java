package com.example.backend.exceptions;

public class RoestereiDoesNotExistException extends Exception {

    public RoestereiDoesNotExistException(String message) {
        super(message);
    }

    public RoestereiDoesNotExistException (String message, Throwable cause) {
        super(message, cause);
    }
}
