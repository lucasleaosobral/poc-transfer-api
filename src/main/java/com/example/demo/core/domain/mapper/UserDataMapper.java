package com.example.demo.core.domain.mapper;

import com.example.demo.core.model.entities.user.UserEntity;
import com.example.demo.core.model.entities.wallet.UserWalletEntity;
import com.example.demo.core.domain.valueobjects.User;
import com.example.demo.core.domain.valueobjects.Wallet;
import com.example.demo.external.controllers.inputs.CreateUserCommand;
import org.springframework.stereotype.Component;

@Component
public class UserDataMapper {

    public UserEntity createUserCommandToUserEntity(CreateUserCommand createUserCommand) {
        return UserEntity.builder()
                .email(createUserCommand.getEmail())
                .password(createUserCommand.getPassword())
                .name(createUserCommand.getName())
                .document(createUserCommand.getDocument())
                .build();
    }

    public User userEntityToUserDomain(UserEntity userEntity) {
        return User.builder()
                .id(userEntity.getId())
                .mail(userEntity.getEmail())
                .accountType(userEntity.getAccountType())
                .wallet(buildUserWallet(userEntity.getWallet()))
                .build();
    }

    private Wallet buildUserWallet(UserWalletEntity userWalletEntity) {
        return Wallet.builder()
                .id(userWalletEntity.getId())
                .balance(userWalletEntity.getBalance())
                .build();
    }
}
