package com.example.WalletSystem.exception;

public class SameWalletTransferException extends RuntimeException {
    public SameWalletTransferException(String message) {
        super(message);
    }
}
