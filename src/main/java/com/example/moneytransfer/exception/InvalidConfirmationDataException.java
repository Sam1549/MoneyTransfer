package com.example.moneytransfer.exception;

public class InvalidConfirmationDataException extends RuntimeException{
    public InvalidConfirmationDataException(String message) {
        super(message);
    }
}
