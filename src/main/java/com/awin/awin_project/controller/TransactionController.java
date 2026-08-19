package com.awin.awin_project.controller;

import com.awin.awin_project.controller.dto.TransactionRequest;
import com.awin.awin_project.controller.dto.TransactionResponse;
import com.awin.awin_project.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public  TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionResponse create(
            @Valid @RequestBody TransactionRequest request
    ) {
        var transaction = transactionService.create(
                request.saleAmount(),
                request.commissionAmount()
        );

        return TransactionResponse.from(transaction);
    }

    @PatchMapping("/{id}/approve")
    public TransactionResponse approve(@PathVariable Long id) {
        var transaction = transactionService.approve(id);

        return TransactionResponse.from(transaction);
    }

    @PatchMapping("/{id}/decline")
    public TransactionResponse decline(@PathVariable Long id) {
        var transaction = transactionService.decline(id);

        return TransactionResponse.from(transaction);
    }
}
