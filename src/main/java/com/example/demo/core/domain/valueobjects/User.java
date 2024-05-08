package com.example.demo.core.domain.valueobjects;


import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.Objects;

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

    public long getId() {
        return id;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public String getMail() {
        return mail;
    }

    public AccountType getAccountType() {
        return accountType;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(mail, user.mail) && accountType == user.accountType && Objects.equals(wallet, user.wallet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, mail, accountType, wallet);
    }
}
