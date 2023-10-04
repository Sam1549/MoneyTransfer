package com.example.moneytransfer.model;


import lombok.Data;
import org.hibernate.validator.constraints.Range;


@Data
public class Amount {
    @Range(min = 1, message = "Сумма не может быть меньше 1")
    private final double value;
    private final String currency;

}
