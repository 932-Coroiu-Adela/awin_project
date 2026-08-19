package com.awin.awin_project.controller;

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

import static org.assertj.core.api.Assertions.assertThat;

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
}
