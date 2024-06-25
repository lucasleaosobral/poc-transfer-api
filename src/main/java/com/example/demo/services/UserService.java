package com.example.demo.services;

import com.example.demo.external.controllers.inputs.AddAmountCommand;
import com.example.demo.external.controllers.inputs.CreateUserCommand;
import com.example.demo.external.controllers.outputs.UserDTO;

public interface UserService {

    Long createUser(CreateUserCommand command);

    void addAmountToWallet(AddAmountCommand addAmountCommand);

    UserDTO getUserFullInfoById(Long id);
}
