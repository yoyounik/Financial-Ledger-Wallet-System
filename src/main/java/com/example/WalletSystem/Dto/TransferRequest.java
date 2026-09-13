package com.example.WalletSystem.Dto;

import java.math.BigDecimal;

public class TransferRequest {

    private BigDecimal amount;


    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
