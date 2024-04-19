package com.example.demo.data.user;

import com.example.demo.domain.entities.User;
import com.example.demo.external.inputs.CreateUserCommand;

public interface UserRepository {

    Long handleCreateUser(CreateUserCommand createUserCommand);

    User getById(Long id);
}
