package com.example.backend.exceptions;

public class KaffeesorteDoesNotExistExeption extends Exception {

    public KaffeesorteDoesNotExistExeption(String message) {
        super(message);
    }

    public KaffeesorteDoesNotExistExeption (String message, Throwable cause) {
        super(message, cause);
    }

}
