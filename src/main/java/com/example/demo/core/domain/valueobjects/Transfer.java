package com.example.demo.core.domain.valueobjects;

import com.example.demo.core.domain.exceptions.TransferException;
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
        this.id = Utils.randomUUID();
        this.amount = this.roundValue(amount);
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
    }

    public void isValid() {
        isNotTheSameAccount();
        canTransfer();
        isNewBalanceValid();
        isAmountGreaterThanZero();
    }

    private void canTransfer() {
        if(this.fromAccount.getAccountType().equals(AccountType.STORE))
            throw new TransferException("Stores can't transfer money.");
    }

    private void isNewBalanceValid() {
        if(this.fromAccount.getWallet().getBalance()
                .subtract(this.amount).compareTo(BigDecimal.ZERO) < 0)
            throw new TransferException("Not enough balance.");
    }

    private void isNotTheSameAccount() {
        if(this.fromAccount.getId() == this.toAccount.getId())
            throw new TransferException("Transfer destination is invalid.");
    }

    private void isAmountGreaterThanZero() {
        if(this.amount.compareTo(BigDecimal.ZERO) <= 0)
            throw new TransferException("Transfer amount is not greater than zero.");
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
