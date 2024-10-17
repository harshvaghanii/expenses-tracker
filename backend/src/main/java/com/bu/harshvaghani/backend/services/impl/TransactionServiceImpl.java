package com.bu.harshvaghani.backend.services.impl;

import com.bu.harshvaghani.backend.dto.TransactionDTO;
import com.bu.harshvaghani.backend.entities.Transaction;
import com.bu.harshvaghani.backend.entities.User;
import com.bu.harshvaghani.backend.exception.ResourceNotFoundException;
import com.bu.harshvaghani.backend.repositories.TransactionRepository;
import com.bu.harshvaghani.backend.repositories.UserRepository;
import com.bu.harshvaghani.backend.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository, UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    @Override
    public TransactionDTO createTransaction(TransactionDTO transactionDTO) {
        User paidBy = userRepository.findById(transactionDTO.getPaidBy().getId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + transactionDTO.getPaidBy().getId()));

        User owedBy = userRepository.findById(transactionDTO.getOwedBy().getId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + transactionDTO.getOwedBy().getId()));

        Transaction transaction = new Transaction();
        transaction.setPaidBy(paidBy);
        transaction.setOwedBy(owedBy);
        transaction.setAmount(transactionDTO.getAmount());
        transaction.setDescription(transactionDTO.getDescription());
        transaction.setTransactionDate(transactionDTO.getTransactionDate());

        Transaction savedTransaction = transactionRepository.save(transaction);
        return new TransactionDTO(new UserDTO(paidBy.getName(), paidBy.getEmail(), paidBy.getCreatedAt(), paidBy.getUpdatedAt()),
                new UserDTO(owedBy.getName(), owedBy.getEmail(), owedBy.getCreatedAt(), owedBy.getUpdatedAt()),
                savedTransaction.getAmount(), savedTransaction.getDescription(),
                savedTransaction.getTransactionDate(), savedTransaction.getCreatedAt(), savedTransaction.getUpdatedAt());
    }

    @Override
    public List<TransactionDTO> getTransactionsForUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        List<Transaction> transactions = transactionRepository.findAllByPaidByOrOwedBy(user, user);
        return transactions.stream().map(transaction -> new TransactionDTO(
                new UserDTO(transaction.getPaidBy().getName(), transaction.getPaidBy().getEmail(), transaction.getPaidBy().getCreatedAt(), transaction.getPaidBy().getUpdatedAt()),
                new UserDTO(transaction.getOwedBy().getName(), transaction.getOwedBy().getEmail(), transaction.getOwedBy().getCreatedAt(), transaction.getOwedBy().getUpdatedAt()),
                transaction.getAmount(), transaction.getDescription(), transaction.getTransactionDate(),
                transaction.getCreatedAt(), transaction.getUpdatedAt()
        )).collect(Collectors.toList());
    }
}
