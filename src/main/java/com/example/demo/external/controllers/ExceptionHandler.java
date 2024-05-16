package com.example.demo.external.controllers;

import com.example.demo.core.domain.exceptions.ExternalServiceException;
import com.example.demo.core.domain.exceptions.TransferException;
import com.example.demo.core.domain.exceptions.UserException;
import com.example.demo.external.api.ExceptionResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleValidationException(MethodArgumentNotValidException e) {
        return new ExceptionResponse("Erro ao validar dados. ",e.getMessage(), HttpStatus.BAD_REQUEST.toString());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleValidationException(DataIntegrityViolationException e) {
        return new ExceptionResponse("Erro tentar inserir dados. ",e.getMessage(), HttpStatus.BAD_REQUEST.toString());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(UserException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse handleValidationException(UserException e) {
        return new ExceptionResponse("Nao encontrado ", e.getMessage(), HttpStatus.NOT_FOUND.toString());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(TransferException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleValidationException(TransferException e) {
        return new ExceptionResponse("Erro ao realizar transferencia. ", e.getMessage(), HttpStatus.BAD_REQUEST.toString());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(ExternalServiceException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse handleValidationException(ExternalServiceException e) {
        return new ExceptionResponse("Erro interno na API. ", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.toString());
    }
}
