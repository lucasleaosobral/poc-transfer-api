package com.example.demo.external.controllers.outputs;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;


@Builder
@Getter
public class UserDTO {

    @Schema(description = "Unique identifier of the customer", example = "1")
    private final long id;

    @Schema(description = "Unique e-mail of the customer", example = "teste@123.com")
    private final String mail;

    @Schema(description = "Type of the customer", example = "USER")
    private final String accountType;

    @Schema(description = "Balance of the user wallet", example = "10.00")
    private final BigDecimal balance;
}
