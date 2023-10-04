package com.example;

import com.example.moneytransfer.MoneyTransferApplication;
import com.example.moneytransfer.model.*;
import com.example.moneytransfer.repository.TransferRepository;
import com.example.moneytransfer.service.TransferService;
import com.example.moneytransfer.validation.ValidationData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.concurrent.ConcurrentHashMap;


@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(classes = MoneyTransferApplication.class)
public class TransferServiceTest {

    @Spy
    TransferRepository transferRepository;
    @Spy
    ValidationData validationData;
    @InjectMocks
    TransferService transferService;

    private static final Transfer VALID_TRANSFER =
            new Transfer("1111111111111111", "01/25", "111", "2222222222222222",
                    new Amount(1000, "RUR"));
    private static final Card CARD_FROM = new Card("1111111111111111", "01/25", "111", new Amount(10_000, "RUR"));
    private static final Card CARD_TO = new Card("2222222222222222", "01/25", "222", new Amount(10_000, "RUR"));
    private static final String CODE = "0000";
    private static final String OPERATION_ID = "1";
    private static final ConcurrentHashMap<String, Transfer> TRANSFER_LIST = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String, Card> CARDS_LIST = new ConcurrentHashMap<>();
    private static final ConfirmInfo CONFIRM_INFO = new ConfirmInfo(CODE,OPERATION_ID);


    @BeforeEach
    public void setUp(){
        CARDS_LIST.put(CARD_FROM.getNumber(),CARD_FROM);
        CARDS_LIST.put(CARD_TO.getNumber(),CARD_TO);
        TRANSFER_LIST.put(OPERATION_ID,VALID_TRANSFER);
        ReflectionTestUtils.setField(transferRepository,"transferList",TRANSFER_LIST);
        ReflectionTestUtils.setField(transferRepository,"cardsList",CARDS_LIST);
    }

    @Test
    public void doTransferTest(){
        Mockito.when(validationData.validationTransfer(VALID_TRANSFER,CARDS_LIST)).thenReturn(true);
        Mockito.when(transferRepository.addTransfer(VALID_TRANSFER)).thenReturn(new TransferResponse(OPERATION_ID));

        String result = transferService.doTransfer(VALID_TRANSFER).getOperationId();

        Assertions.assertEquals(OPERATION_ID,result);
    }

    @Test
    public void doConfirmTest(){
        Mockito.when(validationData.validationConfirm(CONFIRM_INFO,TRANSFER_LIST)).thenReturn(true);
        Mockito.when(transferRepository.confirmOperation(CONFIRM_INFO)).thenReturn(new TransferResponse(OPERATION_ID));

        String result = transferService.doConfirm(CONFIRM_INFO).getOperationId();

        Assertions.assertEquals(OPERATION_ID,result);
    }

}
