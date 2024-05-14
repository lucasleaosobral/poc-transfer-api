package com.example.demo.core.model.entities.user;


import com.example.demo.core.model.entities.wallet.UserWalletEntity;
import com.example.demo.core.domain.valueobjects.AccountType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "users")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "document", nullable = false)
    private String document;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "account_type", nullable = false)
    private String accountType;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "wallet_id", referencedColumnName = "id")
    private UserWalletEntity wallet;


    @PrePersist
    private void prePersist() {
        if (this.document.length() > 11) {
            this.accountType = String.valueOf(AccountType.STORE);
        } else {
            this.accountType = String.valueOf(AccountType.USER);
        }
    }

    public AccountType getAccountType() {
        return AccountType.valueOf(accountType);
    }



}
