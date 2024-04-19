package com.example.demo.data.entities.user;


import com.example.demo.data.entities.wallet.UserWalletEntity;
import com.example.demo.domain.entities.AccountType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserEntity {

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
