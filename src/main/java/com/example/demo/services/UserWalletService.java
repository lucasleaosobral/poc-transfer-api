package com.example.demo.services;

import com.example.demo.core.model.entities.wallet.UserWalletEntity;

import java.math.BigDecimal;
import java.util.UUID;

public interface UserWalletService {

    UserWalletEntity createUserWallet();

    void transferAmount(UUID walletFromId, UUID walletToId, BigDecimal amount);

    void addAmount(UUID walletId, BigDecimal amount);
}
