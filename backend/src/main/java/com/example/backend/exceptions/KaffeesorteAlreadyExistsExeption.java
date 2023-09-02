package com.example.backend.exceptions;

public class KaffeesorteAlreadyExistsExeption extends Exception{

    public KaffeesorteAlreadyExistsExeption(String message) {
        super(message);
    }

    public KaffeesorteAlreadyExistsExeption (String message, Throwable cause) {
        super(message, cause);
    }
}
