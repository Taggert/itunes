package com.itunes.api.controller;

import com.itunes.api.model.InputValidationException;
import com.itunes.api.model.web.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorController {

    @ExceptionHandler(value = InputValidationException.class)
    public ResponseEntity<ErrorResponse> getErrorResponse(InputValidationException e){
        return new ResponseEntity<>(e.getErrorResponse(), HttpStatus.BAD_REQUEST);
    }
}