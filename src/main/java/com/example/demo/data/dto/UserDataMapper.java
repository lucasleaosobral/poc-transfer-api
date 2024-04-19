package com.example.demo.data.dto;

import com.example.demo.data.entities.user.UserEntity;
import com.example.demo.data.entities.wallet.UserWalletEntity;
import com.example.demo.domain.entities.User;
import com.example.demo.domain.entities.Wallet;
import com.example.demo.external.inputs.CreateUserCommand;
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
