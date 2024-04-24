package com.example.demo.services;

import com.example.demo.external.inputs.CreateUserCommand;

public interface UserService {

    Long createUser(CreateUserCommand command);

}
