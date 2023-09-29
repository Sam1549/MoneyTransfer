package com.example.moneytransfer.model;

public class TransferResponse {
    private String operationId;

    // Геттер и сеттер

    public TransferResponse(String operationId) {
        this.operationId = operationId;
    }

    public String getOperationId() {
        return operationId;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
    }
}