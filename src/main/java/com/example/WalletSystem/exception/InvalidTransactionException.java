package com.example.WalletSystem.exception;

public class InvalidTransactionException extends RuntimeException{
    //unchecked exception

    public InvalidTransactionException(String message) {
        super(message);
    }
}
