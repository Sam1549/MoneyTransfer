package com.example;

import com.example.moneytransfer.MoneyTransferApplication;
import com.example.moneytransfer.model.Amount;
import com.example.moneytransfer.model.ConfirmInfo;
import com.example.moneytransfer.model.Transfer;
import com.example.moneytransfer.model.TransferResponse;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = MoneyTransferApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class ContainerTest {

    private static final Transfer TRANSFER = new Transfer("1111111111111111", "01/25", "111", "2222222222222222",
            new Amount(1000, "RUR"));

    @Autowired
    TestRestTemplate restTemplate;

    @Container
    private static final GenericContainer<?> app = new GenericContainer<>("app:latest").withExposedPorts(5500);


    @BeforeAll
    public static void setUp() {
        app.start();
    }

    @AfterAll
    public static void setDown() {
        app.stop();
    }

    @Order(1)
    @Test
    public void appTransfer() {
        ResponseEntity<String> backendEntity = restTemplate.postForEntity(
                "http://localhost:" + app.getMappedPort(5500) + "/transfer", TRANSFER, String.class);
        String response = backendEntity.getBody();

        Assertions.assertEquals("{\"operationId\":\"1\"}", response);
    }

    @Order(2)
    @Test
    void appConfirmOperationTest() {
        ConfirmInfo confirmInfo = new ConfirmInfo("0000", "1");
        ResponseEntity<String> entity = restTemplate.postForEntity("http://localhost:" + app.getMappedPort(5500) + "/confirmOperation", confirmInfo, String.class);

        Assertions.assertEquals("{\"operationId\":\"1\"}", entity.getBody());
    }
}
