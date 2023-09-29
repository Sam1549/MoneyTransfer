package com.example.moneytransfer.exception;

import lombok.Data;
import org.springframework.validation.FieldError;

@Data
public class FieldErrorResponse {
    private final String field;
    private final Object rejectedValue;
    private final String defaultMessage;

    public FieldErrorResponse(FieldError fieldError) {
        this.field = fieldError.getField();
        this.rejectedValue = fieldError.getRejectedValue();
        this.defaultMessage = fieldError.getDefaultMessage();
    }

    @Override
    public String toString() {
        return String.format("[%s : %s] - %s",field,rejectedValue,defaultMessage);
    }
}
