package com.example.demo.external.controllers;


import com.example.demo.core.domain.valueobjects.User;
import com.example.demo.external.controllers.inputs.AddAmountCommand;
import com.example.demo.external.controllers.inputs.CreateUserCommand;
import com.example.demo.external.controllers.outputs.UserDTO;
import com.example.demo.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
    public ResponseEntity addMoneyToWallet(@RequestBody @Valid CreateUserCommand createUserCommand) {

        Long id = userService.createUser(createUserCommand);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/" + id);

        return new ResponseEntity(null, headers, HttpStatus.OK);
    }

    @PostMapping(path = "/wallet")
    @Operation(summary = "Adicionar valores a carteira", description = "Adicionar valores a carteira", responses = {
            @ApiResponse(description = "Successful response", responseCode = "200", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity createUser(@RequestBody @Valid AddAmountCommand addAmountCommand) {

        userService.addAmountToWallet(addAmountCommand);

        return new ResponseEntity(null, HttpStatus.OK);
    }

    @GetMapping(path = "/info/{userId}")
    @Operation(summary = "buscar informações do usuario", description = "retorna informações do usuario e da carteira", responses = {
            @ApiResponse(description = "Successful response", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class)))
    })
    public ResponseEntity getUser(@PathVariable Long userId) {
        UserDTO user = userService.getUserFullInfoById(userId);

        return new ResponseEntity(user, HttpStatus.OK);
    }

}
