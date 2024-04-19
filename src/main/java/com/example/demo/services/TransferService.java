package com.example.demo.services;

import com.example.demo.external.inputs.TransferAmountCommand;

public interface TransferService {

    void transfer(TransferAmountCommand transferAmountCommand);
}
