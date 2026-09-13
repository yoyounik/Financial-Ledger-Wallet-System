package com.example.WalletSystem.service;

import com.example.WalletSystem.entity.Wallet;
import com.example.WalletSystem.repository.WalletRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class WalletServiceTest {
    @Mock
    WalletRepository walletRepository;
    @InjectMocks
    WalletService walletService;

    @Test
    public void depositMoneyToWallet() {
        System.out.println("My first unit test");
        //2nd ye socho
        Wallet wallet = new Wallet();
        wallet.setBalance(new BigDecimal("1000"));

        //3rd ye socho
        Mockito.when(walletRepository.findById(1L)).thenReturn(Optional.of(wallet));

        //4th ye socho
        Mockito.when(walletRepository.save(wallet)).thenReturn(wallet);

        //1st ye likho
        Wallet result = walletService.deposit(1L, new BigDecimal("500"));

        Assertions.assertEquals(new BigDecimal("1500"), result.getBalance());
        Mockito.verify(walletRepository).save(wallet);
    }

    @Test
    public void withdrawMoneyFromWallet() {
        Wallet wallet = new Wallet();
        wallet.setBalance(new BigDecimal("2000"));

        Mockito.when(walletRepository.findById(1L)).thenReturn(Optional.of(wallet));
        Mockito.when(walletRepository.save(wallet)).thenReturn(wallet);

        Wallet result = walletService.withdraw(1L, new BigDecimal("500"));
        Assertions.assertEquals(new BigDecimal("1500"), result.getBalance());
        Mockito.verify(walletRepository).save(wallet);

    }


}
