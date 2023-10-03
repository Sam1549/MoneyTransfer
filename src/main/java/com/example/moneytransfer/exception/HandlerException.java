package com.example.moneytransfer.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class HandlerException {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Error> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());
        log.info(String.valueOf(FieldErrorLog.loggingErrors(ex)));
        return new ResponseEntity<>(getErrorsList(errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidCardDataException.class)
    public ResponseEntity<Error> handleInvalidCardData(InvalidCardDataException ex) {
        List<String> error = new ArrayList<>();
        error.add(ex.getMessage());
        log.info(ex.getMessage());
        return new ResponseEntity<>(getErrorsList(error), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidConfirmationDataException.class)
    public ResponseEntity<Error> handleInvalidConfirmationData(InvalidConfirmationDataException ex) {
        List<String> error = new ArrayList<>();
        error.add(ex.getMessage());
        log.info(ex.getMessage());
        return new ResponseEntity<>(getErrorsList(error), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }


    private Error getErrorsList(List<String> errors) {
        return new Error(errors, "400");
    }

}
