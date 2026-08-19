package com.awin.awin_project.controller.dto;

import com.awin.awin_project.domain.Transaction;
import com.awin.awin_project.domain.TransactionStatus;

import java.math.BigDecimal;

public record TransactionResponse (
        Long id,
        TransactionStatus status,
        BigDecimal saleAmount,
        BigDecimal commissionAmount
) {
    public static TransactionResponse from (Transaction transaction) {
        return new TransactionResponse(
                transaction.getId(),
                transaction.getStatus(),
                transaction.getSaleAmount(),
                transaction.getCommissionAmount()
        );
    }
}
