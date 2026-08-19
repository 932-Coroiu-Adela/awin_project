package com.awin.awin_project.service;

import com.awin.awin_project.domain.Transaction;
import com.awin.awin_project.repository.TransactionRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Transactional
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Transaction create(BigDecimal saleAmount, BigDecimal commissionAmount) {
        Transaction transaction = Transaction.create(saleAmount, commissionAmount);
        return transactionRepository.save(transaction);
    }
}
