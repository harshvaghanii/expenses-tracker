package com.bu.harshvaghani.backend.controller;

import com.bu.harshvaghani.backend.dto.TransactionDTO;
import com.bu.harshvaghani.backend.dto.UserDTO;
import com.bu.harshvaghani.backend.services.TransactionService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TransactionController.class)
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    @Test
    void testCreateTransaction() throws Exception {
        UserDTO paidBy = new UserDTO("John Doe", "john@example.com", null, null);
        UserDTO owedBy = new UserDTO("Jane Doe", "jane@example.com", null, null);
        TransactionDTO transactionDTO = new TransactionDTO(paidBy, owedBy, BigDecimal.valueOf(50), "Dinner",
                LocalDateTime.now(), null, null);

        Mockito.when(transactionService.createTransaction(Mockito.any(TransactionDTO.class))).thenReturn(transactionDTO);

        mockMvc.perform(post("/api/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"paidBy\":{\"name\":\"John Doe\", \"email\":\"john@example.com\"}, \"owedBy\":{\"name\":\"Jane Doe\", \"email\":\"jane@example.com\"}, \"amount\":50, \"description\":\"Dinner\", \"transactionDate\":\"2023-10-31T10:15:30\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.amount").value(50))
                .andExpect(jsonPath("$.description").value("Dinner"))
                .andExpect(jsonPath("$.paidBy.name").value("John Doe"))
                .andExpect(jsonPath("$.owedBy.name").value("Jane Doe"));
    }

    @Test
    void testGetTransactionsForUser() throws Exception {
        UserDTO paidBy = new UserDTO("John Doe", "john@example.com", null, null);
        UserDTO owedBy = new UserDTO("Jane Doe", "jane@example.com", null, null);
        TransactionDTO transaction1 = new TransactionDTO(paidBy, owedBy, BigDecimal.valueOf(50), "Dinner", LocalDateTime.now(), null, null);
        TransactionDTO transaction2 = new TransactionDTO(paidBy, owedBy, BigDecimal.valueOf(30), "Lunch", LocalDateTime.now(), null, null);

        Mockito.when(transactionService.getTransactionsForUser(1L)).thenReturn(List.of(transaction1, transaction2));

        mockMvc.perform(get("/api/transactions/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].amount").value(50))
                .andExpect(jsonPath("$[0].description").value("Dinner"))
                .andExpect(jsonPath("$[1].amount").value(30))
                .andExpect(jsonPath("$[1].description").value("Lunch"));
    }
}
