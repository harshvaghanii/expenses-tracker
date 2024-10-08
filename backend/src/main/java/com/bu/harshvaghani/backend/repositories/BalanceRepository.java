package com.bu.harshvaghani.backend.repositories;

import com.bu.harshvaghani.backend.entities.Balance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BalanceRepository extends JpaRepository<Balance, Long> {
}
