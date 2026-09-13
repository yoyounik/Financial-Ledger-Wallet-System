package com.example.WalletSystem.Dto;

import java.math.BigDecimal;

public class DepositRequest {
//    private Long walletId;
    private BigDecimal amount;

//    public Long getWalletId() {
//        return walletId;
//    }
//
//    public void setWalletId(Long walletId) {
//        this.walletId = walletId;
//    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
