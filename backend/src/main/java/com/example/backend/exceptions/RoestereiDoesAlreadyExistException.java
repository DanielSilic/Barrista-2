package com.example.backend.exceptions;

public class RoestereiDoesAlreadyExistException extends Exception {
    public RoestereiDoesAlreadyExistException(String message) {
        super(message);
    }

    public RoestereiDoesAlreadyExistException (String message, Throwable cause) {
        super(message, cause);
    }
}
