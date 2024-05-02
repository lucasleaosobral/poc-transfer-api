package com.example.demo.services;


import com.example.demo.data.entities.wallet.UserWalletEntity;
import com.example.demo.data.repositories.wallet.WalletRepository;
import com.example.demo.utils.Utils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class UserWalletServiceImpl implements UserWalletService {


    private final WalletRepository walletRepository;

    public UserWalletServiceImpl(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Override
    public UserWalletEntity createUserWallet() {
        return UserWalletEntity.builder()
                .id(Utils.randomUUID())
                .balance(BigDecimal.ZERO)
                .build();
    }

    @Override
    public void transferAmount(UUID from, UUID to, BigDecimal amount) {
        walletRepository.transferAmount(from, to, amount);
    }

    @Override
    public void addAmount(UUID walletId, BigDecimal amount) {
        walletRepository.addAmount(walletId, amount);
    }

}
