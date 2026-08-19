package com.awin.awin_project.controller;

import com.awin.awin_project.domain.Transaction;
import com.awin.awin_project.domain.TransactionStatus;
import com.awin.awin_project.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    TransactionRepository transactionRepository;

    @Test
    void shouldCreatePendingTransaction() throws Exception {
        mockMvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                        """
                            {
                               "saleAmount": 100.00,
                               "commissionAmount": 10.00
                            }    
                        """
                ))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.status").value("PENDING"))
                .andExpect(jsonPath("$.saleAmount").value(100.00))
                .andExpect(jsonPath("$.commissionAmount").value(10.00));

        var transactions = transactionRepository.findAll();

        assertThat(transactions).hasSize(1);
        assertThat(transactions.getFirst().getStatus())
                .isEqualTo(TransactionStatus.PENDING);
    }

    @Test
    void shouldApprovePendingTransaction() throws Exception {
        var transaction = transactionRepository.saveAndFlush(
                Transaction.create(
                        new BigDecimal("160.00"),
                        new BigDecimal("20.00")
                )
        );

        mockMvc.perform(patch("/transactions/{id}/approve", transaction.getId())
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.status").value("APPROVED"))
                .andExpect(jsonPath("$.saleAmount").value(160.00))
                .andExpect(jsonPath("$.commissionAmount").value(20.00));

        var updatedTransaction = transactionRepository.findById(transaction.getId()).orElseThrow();

        assertThat(updatedTransaction.getStatus()).isEqualTo(TransactionStatus.APPROVED);
    }

    @Test
    void shouldReturnNotFoundWhenApprovingUnknownTransaction()
            throws Exception {
        mockMvc.perform(patch("/transactions/{id}/approve", 999))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeclinePendingTransaction() throws Exception {
        var transaction = transactionRepository.saveAndFlush(
                Transaction.create(
                        new BigDecimal("160.00"),
                        new BigDecimal("20.00")
                )
        );

        mockMvc.perform(patch("/transactions/{id}/decline", transaction.getId())
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.status").value("DECLINED"))
                .andExpect(jsonPath("$.saleAmount").value(160.00))
                .andExpect(jsonPath("$.commissionAmount").value(20.00));

        var updatedTransaction = transactionRepository.findById(transaction.getId()).orElseThrow();

        assertThat(updatedTransaction.getStatus()).isEqualTo(TransactionStatus.DECLINED);
    }

    @Test
    void shouldReturnNotFoundWhenDecliningUnknownTransaction()
            throws Exception {
        mockMvc.perform(patch("/transactions/{id}/decline", 999))
                .andExpect(status().isNotFound());
    }
}
