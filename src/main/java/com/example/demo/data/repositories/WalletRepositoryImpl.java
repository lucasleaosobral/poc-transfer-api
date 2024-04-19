package com.example.demo.data.repositories;


import com.example.demo.data.entities.wallet.UserWalletEntity;
import com.example.demo.domain.exceptions.TransferException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Component
public class WalletRepositoryImpl implements WalletRepository {

    private final WalletJpaRepository walletJpaRepository;

    public WalletRepositoryImpl(WalletJpaRepository walletJpaRepository) {
        this.walletJpaRepository = walletJpaRepository;
    }

    @Override
    @Transactional
    public void transferAmount(UUID from, UUID to, BigDecimal amount) {
        UserWalletEntity fromWallet = walletJpaRepository.findById(from).get();
        UserWalletEntity toWallet = walletJpaRepository.findById(to).get();

        fromWallet.updateBalance(fromWallet.getBalance().subtract(amount));
        toWallet.updateBalance(toWallet.getBalance().add(amount));

        walletJpaRepository.save(fromWallet);
        walletJpaRepository.save(toWallet);

    }


}
