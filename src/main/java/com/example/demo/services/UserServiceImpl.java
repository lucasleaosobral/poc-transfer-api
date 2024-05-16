package com.example.demo.services;


import com.example.demo.core.domain.valueobjects.User;
import com.example.demo.core.model.repositories.user.UserRepository;
import com.example.demo.external.controllers.inputs.AddAmountCommand;
import com.example.demo.external.controllers.inputs.CreateUserCommand;
import com.example.demo.services.cache.Cache;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserWalletService userWalletService;

    private final Cache cache;

    public UserServiceImpl(UserRepository userRepository, UserWalletService userWalletService, Cache cache) {
        this.userRepository = userRepository;
        this.userWalletService = userWalletService;
        this.cache = cache;
    }

    @Override
    public Long createUser(CreateUserCommand command) {
        return userRepository.handleCreateUser(command);
    }

    @Override
    public void addAmountToWallet(AddAmountCommand addAmountCommand) {
        User user = userRepository.getById(addAmountCommand.getUserId());
        userWalletService.addAmount(user.getWallet().getId(), addAmountCommand.getAmount());
        cache.flush();
    }
}
