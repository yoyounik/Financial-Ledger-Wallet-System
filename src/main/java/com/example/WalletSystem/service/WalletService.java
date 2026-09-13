package com.example.WalletSystem.service;

import com.example.WalletSystem.Enum.TransactionStatus;
import com.example.WalletSystem.entity.Transaction;
import com.example.WalletSystem.entity.Wallet;
import com.example.WalletSystem.exception.InsufficientBalanceException;
import com.example.WalletSystem.exception.InvalidTransactionException;
import com.example.WalletSystem.exception.SameWalletTransferException;
import com.example.WalletSystem.repository.TransactionRepository;
import com.example.WalletSystem.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class WalletService {
    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    public Wallet deposit(Long walletId, BigDecimal amount) throws InvalidTransactionException {
        if(amount.signum() <= 0){
            throw new InvalidTransactionException("Deposit amount must be positive");
        }

        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new RuntimeException("Wallet not found: " + walletId));


        wallet.setBalance(wallet.getBalance().add(amount));

        Wallet save = walletRepository.save(wallet);

        return save;
    }

    public Wallet withdraw(Long walletId, BigDecimal amount) {
        if(amount.signum() <= 0) {
            throw new InvalidTransactionException("Withdraw amount must be positive");
        }
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new RuntimeException("Wallet not found: " + walletId));

        if(wallet.getBalance().compareTo(amount) >= 0) {
            wallet.setBalance(wallet.getBalance().subtract(amount));
        }
        else{
            throw new InsufficientBalanceException("Sorry your wallet balance is: " + wallet.getBalance());
        }

        Wallet save = walletRepository.save(wallet);

        return save;
    }

    public Wallet createWallet(Long userId) {
        Wallet wallet = new Wallet();
        wallet.setUserId(userId);
        wallet.setCurrency("INR");
        wallet.setBalance(BigDecimal.ZERO);

        Wallet save = walletRepository.save(wallet);
        return save;
    }

    public BigDecimal getWalletBalance(Long walletId) {
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new RuntimeException("Wallet not found: " + walletId));

        return wallet.getBalance();
    }

    public Transaction transfer(long fromWalletId, long toWalletId, BigDecimal amount) {
        if(amount.signum() <= 0){
            throw new InvalidTransactionException("Amount must be positive");
        }
        if(fromWalletId == toWalletId){
            throw new SameWalletTransferException("Cannot transfer to the same wallet: " + fromWalletId);
        }


        Wallet fromWallet = walletRepository.findById(fromWalletId)
                .orElseThrow(() -> new RuntimeException("Transfer from Wallet not found: " + fromWalletId));

        Wallet toWallet = walletRepository.findById(toWalletId)
                .orElseThrow(() -> new RuntimeException("Transfer to Wallet not found: " + toWalletId));


        if(fromWallet.getBalance().compareTo(amount) < 0) {
            Transaction failedTxn = buildTransaction(fromWalletId, toWalletId, amount, TransactionStatus.FAILED);
            transactionRepository.save(failedTxn);
            throw new InsufficientBalanceException("Insufficient balance in your wallet: " + fromWalletId);
        }

        fromWallet.setBalance(fromWallet.getBalance().subtract(amount));
        toWallet.setBalance(toWallet.getBalance().add(amount));

        walletRepository.save(fromWallet);
        walletRepository.save(toWallet);

        Transaction SucceedTxn = buildTransaction(fromWalletId, toWalletId, amount, TransactionStatus.SUCCESS);
        Transaction save = transactionRepository.save(SucceedTxn);
        return save;
    }

    private Transaction buildTransaction(long fromWalletId, long toWalletId, BigDecimal amount, TransactionStatus status) {
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setFromWalletId(fromWalletId);
        transaction.setToWalletId(toWalletId);
        transaction.setTimeStamp(LocalDateTime.now());
        transaction.setStatus(status);

        return transaction;
    }


}
