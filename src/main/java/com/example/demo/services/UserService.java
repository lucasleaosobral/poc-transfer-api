package com.example.demo.services;

import com.example.demo.external.controllers.inputs.AddAmountCommand;
import com.example.demo.external.controllers.inputs.CreateUserCommand;

public interface UserService {

    Long createUser(CreateUserCommand command);

    void addAmountToWallet(AddAmountCommand addAmountCommand);
}
