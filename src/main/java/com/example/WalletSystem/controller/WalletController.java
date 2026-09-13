package com.example.WalletSystem.controller;

import com.example.WalletSystem.Dto.DepositRequest;
import com.example.WalletSystem.Dto.TransferRequest;
import com.example.WalletSystem.entity.Transaction;
import com.example.WalletSystem.entity.Wallet;
import com.example.WalletSystem.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/wallet")
public class WalletController {

    @Autowired
    private WalletService walletService;

    @PostMapping("{walletId}/deposit")
    public Wallet doDeposit(@PathVariable Long walletId, @RequestBody DepositRequest request) {
        return walletService.deposit(walletId, request.getAmount());
    }

    @PostMapping("{walletId}/withdraw")
    public Wallet getWithdrawal(@PathVariable Long walletId, @RequestBody DepositRequest request) {
        return walletService.withdraw(walletId, request.getAmount());
    }
    
    @PostMapping("/{userId}")
    public Wallet createWallet(@PathVariable Long userId) {
        return walletService.createWallet(userId);
    }
    
    @GetMapping("/{walletId}/balance")
    public BigDecimal getWalletBalance(@PathVariable Long walletId) {
        return walletService.getWalletBalance(walletId);
    }

    @PostMapping("/transfer/{fromWalletId}/{toWalletId}")
    public Transaction doTransfer(@PathVariable Long fromWalletId, @PathVariable Long toWalletId,
                                       @RequestBody TransferRequest request) {
        return walletService.transfer(fromWalletId, toWalletId, request.getAmount());
    }

}
