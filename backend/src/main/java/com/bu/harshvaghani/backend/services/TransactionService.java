package com.bu.harshvaghani.backend.services;

import com.bu.harshvaghani.backend.dto.TransactionDTO;

import java.util.List;

public interface TransactionService {
    public List<TransactionDTO> getAllTransactionsByUserId(Long userId);

    public TransactionDTO addTransaction(TransactionDTO transactionDTO);
}
