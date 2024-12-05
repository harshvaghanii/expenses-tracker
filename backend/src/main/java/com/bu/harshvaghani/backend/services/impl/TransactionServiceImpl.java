package com.bu.harshvaghani.backend.services.impl;

import com.bu.harshvaghani.backend.dto.TransactionDTO;
import com.bu.harshvaghani.backend.dto.UserDTO;
import com.bu.harshvaghani.backend.entities.BalanceEntity;
import com.bu.harshvaghani.backend.entities.TransactionEntity;
import com.bu.harshvaghani.backend.entities.UserEntity;
import com.bu.harshvaghani.backend.exception.ResourceNotFoundException;
import com.bu.harshvaghani.backend.exception.UnauthorizedActionException;
import com.bu.harshvaghani.backend.repositories.BalanceRepository;
import com.bu.harshvaghani.backend.repositories.TransactionRepository;
import com.bu.harshvaghani.backend.repositories.UserRepository;
import com.bu.harshvaghani.backend.services.TransactionService;
import com.bu.harshvaghani.backend.utilities.BalanceUtility;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Data
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final BalanceRepository balanceRepository;
    private final JWTServiceImpl jwtService;

    @Override
    public List<TransactionDTO> getAllTransactionsByUserId(Long userId) {
        return List.of();
    }

    @Override
    public TransactionDTO addTransaction(TransactionDTO transactionDTO) {

        // Authorizing the requests
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long requesterId = ((UserEntity) authentication.getPrincipal()).getId();

        Long paidById = transactionDTO.getPaidBy().getId();
        Long owedById = transactionDTO.getOwedBy().getId();

        if (!requesterId.equals(paidById) && !requesterId.equals(owedById)) {
            throw new UnauthorizedActionException("You are not authorized to perform this action.");
        }

        Long ID = transactionDTO.getPaidBy().getId();
        UserEntity paidBy = userRepository.findById(transactionDTO.getPaidBy().getId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + transactionDTO.getPaidBy().getId()));

        UserEntity owedBy = userRepository.findById(transactionDTO.getOwedBy().getId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + transactionDTO.getOwedBy().getId()));
        TransactionEntity toSave = modelMapper.map(transactionDTO, TransactionEntity.class);
        toSave.setPaidBy(paidBy);
        toSave.setOwedBy(owedBy);
        TransactionEntity savedTransaction = transactionRepository.save(toSave);

        // Modify the balance table
        boolean paidByUserOne = transactionDTO.getPaidBy().getId() < transactionDTO.getOwedBy().getId();
        updateBalance(transactionDTO.getPaidBy(), transactionDTO.getOwedBy(), paidByUserOne, transactionDTO.getAmount());

        return modelMapper.map(savedTransaction, TransactionDTO.class);

    }

    public void updateBalance(UserDTO user1, UserDTO user2, boolean paidByUserOne, BigDecimal amount) {
        String balanceId = BalanceUtility.generateBalanceId(user1, user2);
        BalanceEntity balanceEntity = balanceRepository.findByBalanceId(balanceId)
                .orElseGet(() -> BalanceEntity.builder()
                        .balanceId(balanceId)
                        .balanceAmount(BigDecimal.ZERO)
                        .user1(modelMapper.map(user1, UserEntity.class))
                        .user2(modelMapper.map(user2, UserEntity.class))
                        .createdAt(LocalDateTime.now())
                        .createdAt(LocalDateTime.now())
                        .build());

        if (!paidByUserOne) {
            amount = amount.negate();
        }
        balanceEntity.setBalanceAmount(balanceEntity.getBalanceAmount().add(amount));
        balanceEntity.setLastUpdated(LocalDateTime.now());

        balanceRepository.save(balanceEntity);
    }
}
