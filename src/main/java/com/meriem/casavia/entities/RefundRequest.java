package com.meriem.casavia.entities;

import lombok.Data;

@Data
public class RefundRequest {
    private String transactionId;
    private double amount;
    private String currency;
}
