package com.example.demo.core.model.repositories.user;

import com.example.demo.core.domain.valueobjects.User;
import com.example.demo.external.controllers.inputs.CreateUserCommand;

public interface UserRepository {

    Long handleCreateUser(CreateUserCommand createUserCommand);

    User getById(Long id);

    User getFullInfoById(Long id);
}
