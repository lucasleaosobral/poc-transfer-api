package com.example.demo.data.user;

import com.example.demo.data.dto.UserDataMapper;
import com.example.demo.data.entities.user.UserEntity;
import com.example.demo.domain.entities.User;
import com.example.demo.domain.exceptions.UserException;
import com.example.demo.external.inputs.CreateUserCommand;
import com.example.demo.services.UserWalletService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Component
public class UserRepositoryImpl implements UserRepository {

    private final UserDataMapper userDataMapper;
    private final UserJpaRepository userJpaRepository;
    private final UserWalletService userWalletService;

    public UserRepositoryImpl(UserDataMapper userDataMapper, UserJpaRepository userJpaRepository, UserWalletService userWalletService) {
        this.userDataMapper = userDataMapper;
        this.userJpaRepository = userJpaRepository;
        this.userWalletService = userWalletService;
    }

    @Override
    @Transactional
    public Long handleCreateUser(CreateUserCommand createUserCommand) {
        UserEntity userEntity = userDataMapper.createUserCommandToUserEntity(createUserCommand);
        userEntity.setWallet(userWalletService.createUserWallet());
        return userJpaRepository.save(userEntity).getId();
    }

    @Override
    public User getById(Long id) {
        Optional<UserEntity> user = userJpaRepository.findById(id);

        if (user.isEmpty()) {
            throw new UserException("user: " + id + " not found");
        }

        return userDataMapper.userEntityToUserDomain(user.get());
    }

}
