package com.example.moneytransfer.repository;

import com.example.moneytransfer.model.ConfirmInfo;
import com.example.moneytransfer.model.Transfer;
import com.example.moneytransfer.model.TransferResponse;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class TransferRepository {
    private final ConcurrentHashMap<String, Transfer> transferList = new ConcurrentHashMap<>();
    private final AtomicInteger id = new AtomicInteger(0);

    public TransferResponse addTransfer(Transfer transfer) {
        String newId = String.valueOf(id.incrementAndGet());
        transferList.put(newId, transfer);
        return new TransferResponse(newId);
    }

    public TransferResponse confirmOperation(ConfirmInfo confirmInfo) {
        if (confirmInfo.getCode().equals(0000) && transferList.containsKey(confirmInfo.getOperationId())) {
            return new TransferResponse(confirmInfo.getOperationId());
        }
        return null;
    }
}
