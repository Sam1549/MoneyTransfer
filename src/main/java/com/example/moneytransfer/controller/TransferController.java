package com.example.moneytransfer.controller;

import com.example.moneytransfer.model.ConfirmInfo;
import com.example.moneytransfer.model.Transfer;
import com.example.moneytransfer.model.TransferResponse;
import com.example.moneytransfer.service.TransferService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@CrossOrigin
public class TransferController {
    @Autowired
    TransferService transferService;

    @PostMapping("/transfer")
    public ResponseEntity<TransferResponse> tranferMoney(@RequestBody @Valid Transfer transfer) {
//        return ResponseEntity.ok(new TransferResponse("1"));
        return ResponseEntity.ok(transferService.doTransfer(transfer));
    }

    @PostMapping("/confirmOperation")
    public ResponseEntity<TransferResponse> confirmTransfer(@RequestBody ConfirmInfo confirmInfo) {
//        return ResponseEntity.ok(new TransferResponse("1"));
        return ResponseEntity.ok(transferService.doConfirm(confirmInfo));
    }
}
