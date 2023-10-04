package com.example.moneytransfer.model;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class Transfer {
    @Size(min = 16, max = 16, message = "Номер карты отправителя должен быть 16 символов")
    @NotBlank(message = "Номер карты отправителя обязателен")
    private final String cardFromNumber;
    @NotBlank(message = "Срок действия карты отправителя обязателен")
    private final String cardFromValidTill;
    @NotBlank(message = "CVC / CVC2 номер карты отправителя обязателен")
    @Size(min = 3, max = 3, message = "размер CVC / CVC2 должен находиться в диапазоне от 3 до 3")
    private final String cardFromCVV;
    @NotBlank(message = "Номер карты получателя обязателен")
    @Size(min = 16, max = 16, message = "Номер карты получателя должен быть 16 символов")
    private final String cardToNumber;
    @Valid
    private final Amount amount;

}
