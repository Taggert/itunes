package com.itunes.api.model;

import com.itunes.api.model.web.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InputValidationException extends  RuntimeException {

    private ErrorResponse errorResponse;
    private BindingResult result;

    public InputValidationException() {
    }

    public InputValidationException(BindingResult result) {
        this.result = result;
    }

    public ErrorResponse getErrorResponse() {
        if (this.errorResponse == null) {
            this.errorResponse = new ErrorResponse();
        }
        List<FieldError> fieldErrors = this.result.getFieldErrors();
        Map<String, List<String>> errors = new HashMap<>();
        for (FieldError fieldError : fieldErrors) {
            errors.putIfAbsent(fieldError.getField(), new ArrayList<>());
            errors.get(fieldError.getField()).add(fieldError.getDefaultMessage());
        }
        this.errorResponse.setErrors(errors);
        return this.errorResponse;
    }

    public HttpStatus getHttpStatusCode() {
        return HttpStatus.BAD_REQUEST;
    }


}