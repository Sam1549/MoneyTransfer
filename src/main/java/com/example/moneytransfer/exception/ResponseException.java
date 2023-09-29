package com.example.moneytransfer.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class ResponseException {

//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<Map<String, List<String>>> handleValidationErrors(MethodArgumentNotValidException ex) {
//        List<String> errors = ex.getBindingResult().getFieldErrors()
//                .stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());
//        loggingErrors(ex);
//        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
//    }
//
//    private static void loggingErrors(MethodArgumentNotValidException ex) {
//        List<FieldError> fieldErrors = ex.getFieldErrors();
//        Iterator<FieldError> var3 = fieldErrors.iterator();
//        while (var3.hasNext()){
//            FieldErrorResponse fieldErrorResponse = new FieldErrorResponse(var3.next());
//            log.info(String.valueOf(fieldErrorResponse.toString()));
//        }
//    }
//
//    private Map<String, List<String>> getErrorsMap(List<String> errors) {
//        Map<String, List<String>> errorResponse = new HashMap<>();
//        errorResponse.put("errors", errors);
//        return errorResponse;
//    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Error> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());
        loggingErrors(ex);
        return new ResponseEntity<>(getErrorsList(errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    private static void loggingErrors(MethodArgumentNotValidException ex) {
        List<FieldError> fieldErrors = ex.getFieldErrors();
        Iterator<FieldError> var3 = fieldErrors.iterator();
        while (var3.hasNext()) {
            FieldErrorResponse fieldErrorResponse = new FieldErrorResponse(var3.next());
            log.info(String.valueOf(fieldErrorResponse.toString()));
        }
    }

    private Error getErrorsList(List<String> errors) {
        return new Error(errors,"400");
    }

}
