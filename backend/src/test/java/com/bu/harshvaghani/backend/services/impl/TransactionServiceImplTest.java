package com.bu.harshvaghani.backend.services.impl;

import com.bu.harshvaghani.backend.dto.TransactionDTO;
import com.bu.harshvaghani.backend.entities.Transaction;
import com.bu.harshvaghani.backend.entities.User;
import com.bu.harshvaghani.backend.repositories.TransactionRepository;
import com.bu.harshvaghani.backend.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransactionServiceImplTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateTransaction() {
        // Arrange
        Long paidById = 1L;
        Long owedById = 2L;

        User paidBy = new User();
        paidBy.setId(paidById);
        User owedBy = new User();
        owedBy.setId(owedById);

        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setPaidBy(new UserDTO(paidBy.getName(), paidBy.getEmail(), null, null));
        transactionDTO.setOwedBy(new UserDTO(owedBy.getName(), owedBy.getEmail(), null, null));
        transactionDTO.setAmount(BigDecimal.TEN);
        transactionDTO.setTransactionDate(LocalDateTime.now());

        when(userRepository.findById(paidById)).thenReturn(java.util.Optional.of(paidBy));
        when(userRepository.findById(owedById)).thenReturn(java.util.Optional.of(owedBy));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(new Transaction());

        // Act
        TransactionDTO result = transactionService.createTransaction(transactionDTO);

        // Assert
        assertNotNull(result);
        assertEquals(BigDecimal.TEN, result.getAmount());
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }
}
