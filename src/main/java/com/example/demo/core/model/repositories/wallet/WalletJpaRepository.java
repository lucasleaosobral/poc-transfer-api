package com.example.demo.core.model.repositories.wallet;

import com.example.demo.core.model.entities.wallet.UserWalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WalletJpaRepository extends JpaRepository<UserWalletEntity, UUID> {
}
