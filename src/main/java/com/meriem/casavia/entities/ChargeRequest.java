package com.meriem.casavia.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ChargeRequest {
    private int amount;
    private String currency;
    private String number;
    private int expMonth;
    private int expYear;
    private String cvc;

}
