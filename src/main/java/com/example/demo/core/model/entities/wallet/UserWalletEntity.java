package com.example.demo.core.model.entities.wallet;


import com.example.demo.core.model.entities.user.UserEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "wallets")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserWalletEntity {

    @Id
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "wallet")
    @JsonBackReference
    private UserEntity user;

    @Column(name = "balance", columnDefinition = "NUMERIC(10,2) DEFAULT 0")
    private BigDecimal balance;

    public UUID getId() {
        return id;
    }

    public void updateBalance(BigDecimal newBalance) {
        this.balance = newBalance;
    }
}
