package com.example.moneytransfer.service;

import com.example.moneytransfer.model.ConfirmInfo;
import com.example.moneytransfer.model.Transfer;
import com.example.moneytransfer.model.TransferResponse;
import com.example.moneytransfer.repository.TransferRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TransferService {

    @Autowired
    TransferRepository transferRepository;


    public TransferResponse doTransfer(Transfer transfer) {
        return transferRepository.addTransfer(transfer);
    }

    public TransferResponse doConfirm(ConfirmInfo confirmInfo) {
        return transferRepository.confirmOperation(confirmInfo);
    }
}
