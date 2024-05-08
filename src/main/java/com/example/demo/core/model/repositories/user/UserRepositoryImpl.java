package com.example.demo.core.model.repositories.user;

import com.example.demo.core.domain.mapper.UserDataMapper;
import com.example.demo.core.model.entities.user.UserEntity;
import com.example.demo.core.domain.valueobjects.User;
import com.example.demo.core.domain.exceptions.UserException;
import com.example.demo.external.controllers.inputs.CreateUserCommand;
import com.example.demo.services.UserWalletService;
import com.example.demo.services.cache.Cache;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Component
@EnableCaching
public class UserRepositoryImpl implements UserRepository {

    private final UserDataMapper userDataMapper;
    private final UserJpaRepository userJpaRepository;
    private final UserWalletService userWalletService;
    private final Cache cache;
    private final ObjectMapper objectMapper;

    public UserRepositoryImpl(UserDataMapper userDataMapper, UserJpaRepository userJpaRepository, UserWalletService userWalletService, Cache cache, ObjectMapper objectMapper) {
        this.userDataMapper = userDataMapper;
        this.userJpaRepository = userJpaRepository;
        this.userWalletService = userWalletService;
        this.cache = cache;
        this.objectMapper = objectMapper;
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

        Optional<UserEntity> fromCache = cache.get(String.valueOf(id), UserEntity.class);

        if (fromCache.isPresent())
            return userDataMapper.userEntityToUserDomain(fromCache.get());

        Optional<UserEntity> user = userJpaRepository.findById(id);

        if (user.isEmpty()) {
            throw new UserException("user: " + id + " not found");
        }

        cache.put(String.valueOf(id), user.get());

        return userDataMapper.userEntityToUserDomain(user.get());
    }

}
