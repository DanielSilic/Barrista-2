package com.example.backend;

import com.example.backend.exceptions.KaffeesorteAlreadyExistsException;
import com.example.backend.exceptions.KaffeesorteDoesNotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerAdviser {
        @ExceptionHandler(KaffeesorteAlreadyExistsException.class)
        private ResponseEntity<Object> handleException(KaffeesorteAlreadyExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);  // 409 Conflict status response
        }

        @ExceptionHandler(KaffeesorteDoesNotExistException.class)
        private ResponseEntity<Object> handleExeption(KaffeesorteDoesNotExistException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND); // 404 response
        }

}



