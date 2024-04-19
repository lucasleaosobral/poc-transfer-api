package com.example.demo.data.repositories;

import java.math.BigDecimal;
import java.util.UUID;

public interface WalletRepository {

    void transferAmount(UUID from, UUID to, BigDecimal amount);
}
