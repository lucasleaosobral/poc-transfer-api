package com.example.demo.external.controllers.inputs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class CreateUserCommand {
    @NotBlank(message = "name must not be blank")
    private String name;
    @Size(min = 11, max = 14, message = "document must be in 11 to 14 range" )
    private String document;
    @NotBlank(message = "email must not be blank")
    private String email;
    @NotBlank(message = "password must not be blank")
    private String password;
}
