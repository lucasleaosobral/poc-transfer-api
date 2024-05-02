package com.example.demo.services;


import com.example.demo.data.repositories.user.UserRepository;
import com.example.demo.external.inputs.CreateUserCommand;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Long createUser(CreateUserCommand command) {
        return userRepository.handleCreateUser(command);
    }
}
