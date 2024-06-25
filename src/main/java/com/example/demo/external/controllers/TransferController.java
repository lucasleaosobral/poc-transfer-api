package com.example.demo.external.controllers;

import com.example.demo.external.controllers.inputs.TransferAmountCommand;
import com.example.demo.services.TransferService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transfer")
public class TransferController {


    private final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }


    @Operation(summary = "Transferir saldo", description = "transfere saldo entre usuarios", responses = {
            @ApiResponse(description = "Successful response", responseCode = "200", content = @Content(mediaType = "application/json"))
    })
    @PostMapping
    public ResponseEntity transfer(@RequestBody @Valid TransferAmountCommand transferAmountCommand) {

        transferService.transfer(transferAmountCommand);
        return new ResponseEntity(null, null, HttpStatus.OK);
    }
}
