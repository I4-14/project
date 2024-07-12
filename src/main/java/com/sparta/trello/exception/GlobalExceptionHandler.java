package com.sparta.trello.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({CustomException.class})
    protected ResponseEntity<ExceptionDto> handleCustomException(CustomException ex) {
        ExceptionDto response = new ExceptionDto(ex.getStatusEnum());
        return new ResponseEntity<>(response, ex.getStatusEnum().getHttpStatus());

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity handleValidationExceptions(MethodArgumentNotValidException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ex.getBindingResult().getFieldError().getDefaultMessage());
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity handleException(Exception ex) {
        ExceptionDto response = new ExceptionDto(ex.getMessage(),400);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(response);
    }
}
