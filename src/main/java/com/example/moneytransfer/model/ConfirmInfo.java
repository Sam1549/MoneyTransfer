package com.example.moneytransfer.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ConfirmInfo {
    private String code;
    private String operationId;
}
