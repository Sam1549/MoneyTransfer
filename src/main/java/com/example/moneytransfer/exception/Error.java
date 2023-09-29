package com.example.moneytransfer.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.validation.FieldError;

import java.util.Iterator;
import java.util.List;

@Data
@AllArgsConstructor
public class Error {
    private List<String> message;
    private String id;





    @Override
    public String toString() {
        return "message:" + message + "\n" +
                "id:" + id;
    }
}
