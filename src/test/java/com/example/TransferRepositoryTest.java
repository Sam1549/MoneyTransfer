package com.example;

import com.example.moneytransfer.MoneyTransferApplication;
import com.example.moneytransfer.model.Amount;
import com.example.moneytransfer.model.Card;
import com.example.moneytransfer.model.ConfirmInfo;
import com.example.moneytransfer.model.Transfer;
import com.example.moneytransfer.repository.TransferRepository;
import com.example.moneytransfer.validation.ValidationData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.concurrent.ConcurrentHashMap;

@SpringBootTest(classes = MoneyTransferApplication.class)
@RunWith(MockitoJUnitRunner.class)
public class TransferRepositoryTest {
    @InjectMocks
    TransferRepository transferRepository;
    @Spy
    ValidationData validationData;


    private static final Transfer VALID_TRANSFER =
            new Transfer("1111111111111111", "01/25", "111", "2222222222222222",
                    new Amount(1000, "RUR"));
    private static final Card CARD_FROM = new Card("1111111111111111", "01/25", "111", new Amount(10_000, "RUR"));
    private static final Card CARD_TO = new Card("2222222222222222", "01/25", "222", new Amount(10_000, "RUR"));
    private static final String CODE = "0000";
    private static final String OPERATION_ID = "1";
    private static final ConcurrentHashMap<String, Transfer> TRANSFER_LIST = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String, Card> CARDS_LIST = new ConcurrentHashMap<>();
    private static final ConfirmInfo CONFIRM_INFO = new ConfirmInfo(CODE, OPERATION_ID);


    @BeforeEach
    public void setUp() {
        CARDS_LIST.put(CARD_FROM.getNumber(), CARD_FROM);
        CARDS_LIST.put(CARD_TO.getNumber(), CARD_TO);
        TRANSFER_LIST.put(OPERATION_ID, VALID_TRANSFER);
        ReflectionTestUtils.setField(transferRepository, "transferList", TRANSFER_LIST);
        ReflectionTestUtils.setField(transferRepository, "cardsList", CARDS_LIST);
    }

    @Test
    public void addTransferTest() {
        Mockito.when(validationData.validationTransfer(VALID_TRANSFER, CARDS_LIST)).thenReturn(true);

        String result = transferRepository.addTransfer(VALID_TRANSFER).getOperationId();

        Assertions.assertEquals(OPERATION_ID, result);
    }

    @Test
    public void confirmOperationTest() {
        String result = transferRepository.confirmOperation(CONFIRM_INFO).getOperationId();

        Assertions.assertEquals(OPERATION_ID, result);
    }
}
