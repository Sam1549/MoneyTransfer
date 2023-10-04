package com.example.moneytransfer.model;


import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class Card {
    private String number;
    private String validTill;
    private String cvv;
    private Amount amount;
}
