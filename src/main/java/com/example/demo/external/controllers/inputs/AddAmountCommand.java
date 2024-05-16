package com.example.demo.external.controllers.inputs;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class AddAmountCommand {

        @NotNull(message = "user id must not be null")
        @Schema(example = "1", required = true)
        private Long userId;

        @NotNull(message = "must be a valid amount to add")
        @Schema(description = "document", example = "100.00", required = true)
        @Positive
        private BigDecimal amount;
}
