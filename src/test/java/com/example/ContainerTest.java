package com.example;

import com.example.moneytransfer.MoneyTransferApplication;
import com.example.moneytransfer.model.Amount;
import com.example.moneytransfer.model.Transfer;
import com.example.moneytransfer.model.TransferResponse;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(classes = MoneyTransferApplication.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class ContainerTest {

    private static final Transfer TRANSFER = new Transfer("1111111111111111", "01/25", "111", "2222222222222222",
            new Amount(1000, "RUR"));

    @Autowired
    TestRestTemplate restTemplate;

    @Container
   private static final GenericContainer<?> app = new GenericContainer<>("app:latest").withExposedPorts(5500);

    @BeforeAll
    public static void setUp(){
        app.start();
    }
    @AfterAll
    public static void setDown(){
        app.stop();
    }

    @Test
    public void appTransfer(){
        TransferResponse response = restTemplate.postForObject("http://localhost:"+app.getMappedPort(5500) + "/transfer",TRANSFER,TransferResponse.class);
        Assertions.assertEquals("1",response.getOperationId());
    }
}
