package com.example.demo.external.controllers.inputs;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransferAmountCommand {

    @NotNull
    @Positive
    private Double value;
    @NotNull
    private Long payer;
    @NotNull
    private Long payee;

    public Double getValue() {
        return value;
    }

    public Long getPayer() {
        return payer;
    }

    public Long getPayee() {
        return payee;
    }
}
