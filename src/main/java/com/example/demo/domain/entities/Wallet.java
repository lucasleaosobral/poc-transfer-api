package com.example.demo.domain.entities;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
@Getter
public class Wallet {

    private UUID id;
    private BigDecimal balance;
}
