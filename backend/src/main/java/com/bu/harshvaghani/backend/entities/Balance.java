package com.bu.harshvaghani.backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Balance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "balance_id")
    Long id;

    @ManyToOne
    @JoinColumn(referencedColumnName = "user_id", nullable = false)
    private User user1;

    @ManyToOne
    @JoinColumn(referencedColumnName = "user_id", nullable = false)
    private User user2;

    @Column(nullable = false)
    private BigDecimal balanceAmount;

    @UpdateTimestamp
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;


}
