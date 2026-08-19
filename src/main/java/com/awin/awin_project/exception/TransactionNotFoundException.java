package com.awin.awin_project.exception;

public class TransactionNotFoundException extends RuntimeException {
    public TransactionNotFoundException(Long id) {

        super("No transaction with id: " + id + " exists in the database.");
    }
}
