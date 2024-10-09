package com.bu.harshvaghani.backend.services;

import com.bu.harshvaghani.backend.dto.TransactionDTO;

import java.util.List;

public interface TransactionService {
    TransactionDTO createTransaction(TransactionDTO transactionDTO);

    List<TransactionDTO> getTransactionsForUser(Long userId);


}
