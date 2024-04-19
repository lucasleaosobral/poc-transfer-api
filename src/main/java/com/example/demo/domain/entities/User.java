package com.example.demo.domain.entities;


import lombok.Builder;

import java.math.BigDecimal;

@Builder
public class User {

    private final long id;
    private final AccountType accountType;
    private final Wallet wallet;

    public User(long id, AccountType accountType, Wallet wallet) {
        this.id = id;
        this.accountType = accountType;
        this.wallet = wallet;
    }

    public boolean canTransfer() {
        return this.accountType.equals(AccountType.USER)
                && this.wallet.getBalance().compareTo(BigDecimal.ZERO) > 0;
    }

    public long getId() {
        return id;
    }

    public Wallet getWallet() {
        return wallet;
    }
}
