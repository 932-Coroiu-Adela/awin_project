package com.awin.awin_project.exception;

public class TransactionAlreadyDecidedException extends RuntimeException {
    public TransactionAlreadyDecidedException(Long id) {
        super("Transaction with id " + id + " has already had its status changed.");
    }
}
