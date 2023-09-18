package com.example.backend;

import com.example.backend.exceptions.KaffeesorteAlreadyExistsException;
import com.example.backend.exceptions.KaffeesorteDoesNotExistException;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerAdviser {
        @ExceptionHandler(KaffeesorteAlreadyExistsException.class)
        public ResponseEntity<String> handleKaffeesorteAlreadyExistsException(KaffeesorteAlreadyExistsException e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler(KaffeesorteDoesNotExistException.class)
        public ResponseEntity<Object> handleExeption(KaffeesorteDoesNotExistException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND); // 404
        }


}



