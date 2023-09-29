package com.example.moneytransfer.model;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.springframework.validation.annotation.Validated;


@Data
public class Amount {
    @Range(min = 1,message = "Сумма не может быть меньше 1")
    private final int value;
    private final String currency;
}
