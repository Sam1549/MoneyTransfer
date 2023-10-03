package com.example.moneytransfer.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.bind.MethodArgumentNotValidException;

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
