package com.bu.harshvaghani.backend.controller;

import com.bu.harshvaghani.backend.dto.TransactionDTO;
import com.bu.harshvaghani.backend.services.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<TransactionDTO> createTransaction(@RequestBody TransactionDTO transactionDTO) {
        TransactionDTO createdTransaction = transactionService.createTransaction(transactionDTO);
        return new ResponseEntity<>(createdTransaction, HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TransactionDTO>> getTransactionsForUser(@PathVariable Long userId) {
        List<TransactionDTO> transactions = transactionService.getTransactionsForUser(userId);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }
}
