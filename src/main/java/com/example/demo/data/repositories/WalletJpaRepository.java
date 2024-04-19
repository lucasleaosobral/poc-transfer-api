package com.example.demo.data.repositories;

import com.example.demo.data.entities.wallet.UserWalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WalletJpaRepository extends JpaRepository<UserWalletEntity, UUID> {
}
