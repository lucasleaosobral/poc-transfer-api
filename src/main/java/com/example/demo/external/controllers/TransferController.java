package com.example.demo.external.controllers;

import com.example.demo.external.controllers.inputs.TransferAmountCommand;
import com.example.demo.services.TransferService;
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

    @PostMapping
    public ResponseEntity transfer(@RequestBody @Valid TransferAmountCommand transferAmountCommand) {

        transferService.transfer(transferAmountCommand);
        return new ResponseEntity(null, null, HttpStatus.OK);
    }
}
