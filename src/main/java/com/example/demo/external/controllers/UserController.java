package com.example.demo.external.controllers;


import com.example.demo.external.inputs.CreateUserCommand;
import com.example.demo.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping
    @Operation(summary = "Criar novo usuario", description = "Cria um novo usuario", responses = {
            @ApiResponse(description = "Successful response with location header", responseCode = "200", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity createUser(@RequestBody @Valid CreateUserCommand createUserCommand) {

        Long id = userService.createUser(createUserCommand);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/" + id);

        return new ResponseEntity(null, headers, HttpStatus.OK);
    }
}
