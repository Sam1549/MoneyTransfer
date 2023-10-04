package com.example.moneytransfer.validation;

import com.example.moneytransfer.exception.InvalidCardDataException;
import com.example.moneytransfer.exception.InvalidConfirmationDataException;
import com.example.moneytransfer.model.Amount;
import com.example.moneytransfer.model.Card;
import com.example.moneytransfer.model.ConfirmInfo;
import com.example.moneytransfer.model.Transfer;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class ValidationData {

    public boolean validationConfirm(ConfirmInfo confirmInfo, ConcurrentHashMap<String, Transfer> transferList) {
        if (!confirmInfo.getCode().equals("0000")) {
            throw new InvalidConfirmationDataException("Неверный код подтверждения");
        } else if (!transferList.containsKey(confirmInfo.getOperationId())) {
            throw new InvalidConfirmationDataException("Неверный id операции");
        }

        return true;

    }

    public boolean validationTransfer(Transfer transfer, ConcurrentHashMap<String, Card> cardsList) {
        if (transfer.getCardFromNumber().isEmpty() && transfer.getCardToNumber().isEmpty()) {
            throw new InvalidCardDataException("Номер карты не может быть пустым");
        } else if (!transfer.getCardFromNumber().matches("[0-9]{16}")) {
            throw new InvalidCardDataException("Номер карты отправителя должна быть 16 фицр");
        } else if (!transfer.getCardToNumber().matches("[0-9]{16}")) {
            throw new InvalidCardDataException("Номер карты получателя должна быть 16 цифр");
        } else if (transfer.getCardFromNumber().equals(transfer.getCardToNumber())) {
            throw new InvalidCardDataException("Номер карты отправителя и получателя должны быть разными");
        } else if (!transfer.getCardFromCVV().matches("[0-9]{3}")) {
            throw new InvalidCardDataException("CVV должен состоять из 3 цифр");
        } else if (!cardsList.containsKey(transfer.getCardFromNumber())) {
            throw new InvalidCardDataException(transfer.getCardFromNumber() + " не найдена в базе данных");
        } else if (!cardsList.containsKey(transfer.getCardToNumber())) {
            throw new InvalidCardDataException(transfer.getCardToNumber() + " не найдена в базе данных");
        } else if (!cardsList.get(transfer.getCardToNumber()).getAmount().getCurrency().equals(cardsList.get(transfer.getCardFromNumber()).getAmount().getCurrency())) {
            throw new InvalidCardDataException("У получателя нет счета с данной валютой");
        } else {
            double valueCardFrom = cardsList.get(transfer.getCardFromNumber()).getAmount().getValue();
            double transferValueWithFee = (transfer.getAmount().getValue() / 100) + ((transfer.getAmount().getValue() / 100) / 100);
            if (valueCardFrom < transferValueWithFee) {
                throw new InvalidCardDataException("Недостаточно средств для перевода");
            }
        }
        return true;
    }

    public boolean transferExecute(Transfer transfer, ConcurrentHashMap<String, Card> cardsList) {
        double CardFromOldValue = cardsList.get(transfer.getCardFromNumber()).getAmount().getValue();
        double CardToOldValue = cardsList.get(transfer.getCardToNumber()).getAmount().getValue();
        double transferValue = (transfer.getAmount().getValue() / 100);
        double transferValueWithFee = transferValue + (transferValue / 100);
        String currency = transfer.getAmount().getCurrency();
        cardsList.get(transfer.getCardFromNumber()).setAmount(new Amount(CardFromOldValue - transferValueWithFee, currency));
        cardsList.get(transfer.getCardToNumber()).setAmount(new Amount(CardToOldValue + transferValue, currency));
        return true;
    }
}
