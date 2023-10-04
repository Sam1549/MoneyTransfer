package com.example.moneytransfer.exception;

import lombok.Data;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Data
public class FieldErrorLog {
    private final String field;
    private final Object rejectedValue;
    private final String defaultMessage;

    public FieldErrorLog(FieldError fieldError) {
        this.field = fieldError.getField();
        this.rejectedValue = fieldError.getRejectedValue();
        this.defaultMessage = fieldError.getDefaultMessage();
    }

    public static List<FieldErrorLog> loggingErrors(MethodArgumentNotValidException ex) {
        List<FieldError> fieldErrors = ex.getFieldErrors();
        List<FieldErrorLog> fieldErrorLog = new ArrayList<>();
        Iterator<FieldError> var3 = fieldErrors.iterator();
        while (var3.hasNext()) {
            fieldErrorLog.add(new FieldErrorLog(var3.next()));
        }
        return fieldErrorLog;
    }


    @Override
    public String toString() {
        return String.format("[%s : %s] - %s", field, rejectedValue, defaultMessage);
    }
}
