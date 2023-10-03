package com.example.moneytransfer.repository;

import com.example.moneytransfer.exception.InvalidCardDataException;
import com.example.moneytransfer.exception.InvalidConfirmationDataException;
import com.example.moneytransfer.model.*;
import com.example.moneytransfer.validation.ValidationData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Repository
public class TransferRepository {
    private final ConcurrentHashMap<String, Transfer> transferList = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Card> cardsList = init();
    private final AtomicInteger id = new AtomicInteger(0);

    public TransferResponse addTransfer(Transfer transfer) {
        if (ValidationData.validationTransfer(transfer, cardsList)) {
            String newId = String.valueOf(id.incrementAndGet());
            transferList.put(newId, transfer);
            log.info("Зарегестрирован новый перевод: Operation id {}, CardFrom {}, CardTo {}, amount {}, currency {}, commission {}",
                    newId, transfer.getCardFromNumber(), transfer.getCardToNumber(), transfer.getAmount().getValue() / 100, transfer.getAmount().getCurrency(), (transfer.getAmount().getValue() / 100) / 100);
            return new TransferResponse(newId);
        }
        throw new InvalidCardDataException("Ошибка перевода");
    }

    public TransferResponse confirmOperation(ConfirmInfo confirmInfo) {
        if (ValidationData.validationConfirm(confirmInfo, transferList)) {
            if (ValidationData.transferExecute(transferList.get(confirmInfo.getOperationId()), cardsList)) {
                log.info("Перевод подтвержден: Operation id {}, CardFrom {}, CardTo {}, amount {}, currency {}, commission {}",
                        confirmInfo.getOperationId(), transferList.get(confirmInfo.getOperationId()).getCardFromNumber(), transferList.get(confirmInfo.getOperationId()).getCardToNumber(), transferList.get(confirmInfo.getOperationId()).getAmount().getValue() / 100, transferList.get(confirmInfo.getOperationId()).getAmount().getCurrency(), (transferList.get(confirmInfo.getOperationId()).getAmount().getValue() / 100) / 100);
                System.out.println("Баланс 2 карты: " + cardsList.get(transferList.get(confirmInfo.getOperationId()).getCardToNumber()).getAmount().getValue());
                System.out.println("Баланс 1 карты: " + cardsList.get(transferList.get(confirmInfo.getOperationId()).getCardFromNumber()).getAmount().getValue());
                return new TransferResponse(confirmInfo.getOperationId());
            }
            throw new InvalidConfirmationDataException("Ошибка перевода");
        }
        throw new InvalidConfirmationDataException("Ошибка подтверждения перевода");
    }


    private ConcurrentHashMap<String, Card> init() {
        ConcurrentHashMap<String, Card> cardsList = new ConcurrentHashMap<>();
        cardsList.put("1111111111111111", new Card("1111111111111111", "01/25", "111", new Amount(10_000, "RUR")));
        cardsList.put("2222222222222222", new Card("2222222222222222", "01/25", "222", new Amount(10_000, "RUR")));
        return cardsList;
    }
}
