package com.example.demo.external.controllers.inputs;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TransferAmountCommand {

    @NotNull
    @Positive
    @Schema(description = "amount to transfer", example = "100.00", required = true)
    private Double value;
    @NotNull
    @Schema(description = "from user", example = "1", required = true)
    private Long payer;
    @NotNull
    @Schema(description = "to user", example = "2", required = true)
    private Long payee;

}
