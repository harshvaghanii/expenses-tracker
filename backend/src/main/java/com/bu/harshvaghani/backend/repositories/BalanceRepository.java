package com.bu.harshvaghani.backend.repositories;

import com.bu.harshvaghani.backend.entities.BalanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BalanceRepository extends JpaRepository<BalanceEntity, Long> {
    Optional<BalanceEntity> findByBalanceId(String balanceId);
}

