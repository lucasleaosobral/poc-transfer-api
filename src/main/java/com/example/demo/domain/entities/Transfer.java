package com.example.demo.domain.entities;

import com.example.demo.utils.Utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

public class Transfer {

    private final UUID id;
    private final BigDecimal amount;
    private final User fromAccount;
    private final User toAccount;

    public Transfer(User fromAccount, User toAccount, double amount) {
        this.id = Utils.randomUUID();;
        this.amount = this.roundValue(amount);
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
    }

    public boolean isValid() {
        return this.fromAccount.canTransfer()
                && isNewBalanceValid()
                && isAmountGreaterThanZero()
                && isNotTheSameAccount();
    }

    private boolean isNewBalanceValid() {
        return this.fromAccount.getWallet().getBalance()
                .subtract(this.amount).compareTo(BigDecimal.ZERO) >= 0;
    }

    private boolean isNotTheSameAccount() {
        return this.fromAccount.getId() != this.toAccount.getId();
    }

    private boolean isAmountGreaterThanZero() {
        return this.amount.compareTo(BigDecimal.ZERO) > 0;
    }

    private BigDecimal roundValue(double amount) {
        return BigDecimal.valueOf(amount).setScale(2, RoundingMode.HALF_EVEN);
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public UUID getId() {
        return id;
    }
}
