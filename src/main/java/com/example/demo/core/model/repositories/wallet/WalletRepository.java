package com.example.demo.core.model.repositories.wallet;

import java.math.BigDecimal;
import java.util.UUID;

public interface WalletRepository {

    void transferAmount(UUID from, UUID to, BigDecimal amount);

    void addAmount(UUID walletId, BigDecimal amount);
}
