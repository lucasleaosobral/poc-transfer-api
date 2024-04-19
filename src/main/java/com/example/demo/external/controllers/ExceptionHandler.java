package com.example.demo.external.controllers;

import com.example.demo.domain.exceptions.ExternalServiceException;
import com.example.demo.domain.exceptions.TransferException;
import com.example.demo.domain.exceptions.UserException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationException(MethodArgumentNotValidException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Validation error: " + e.getBindingResult().getFieldError().getDefaultMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleValidationException(DataIntegrityViolationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Data violation error: " + e.getMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(UserException.class)
    public ResponseEntity<String> handleValidationException(UserException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(TransferException.class)
    public ResponseEntity<String> handleValidationException(TransferException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(ExternalServiceException.class)
    public ResponseEntity<String> handleValidationException(ExternalServiceException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
}
