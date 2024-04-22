package com.example.demo.domain.entities;


import lombok.Builder;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@NoArgsConstructor(force = true)
public class User {

    private final long id;
    private final String mail;
    private final AccountType accountType;
    private final Wallet wallet;

    public User(long id, String mail, AccountType accountType, Wallet wallet) {
        this.id = id;
        this.mail = mail;
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

    public String getMail() {
        return mail;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", mail='" + mail + '\'' +
                ", accountType=" + accountType +
                ", wallet=" + wallet +
                '}';
    }
}
