package com.example.demo.external.controllers.inputs;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class CreateUserCommand {

    @Schema(description = "username", example = "lucas")
    private String name;

    @Size(min = 11, max = 14, message = "document must be in 11 to 14 range" )
    @Schema(description = "document", example = "79515189055", required = true)
    private String document;

    @NotBlank(message = "email must not be blank")
    @Schema(description = "document", example = "teste@teste.com", required = true)
    private String email;

    @NotBlank(message = "password must not be blank")
    private String password;
}
