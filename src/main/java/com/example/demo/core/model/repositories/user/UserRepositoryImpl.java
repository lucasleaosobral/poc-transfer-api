package com.example.demo.core.model.repositories.user;

import com.example.demo.core.domain.mapper.UserDataMapper;
import com.example.demo.core.model.entities.user.UserEntity;
import com.example.demo.core.domain.valueobjects.User;
import com.example.demo.core.domain.exceptions.UserException;
import com.example.demo.external.controllers.inputs.CreateUserCommand;
import com.example.demo.services.UserWalletService;
import com.example.demo.services.cache.Cache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Component
@EnableCaching
@Slf4j
public class UserRepositoryImpl implements UserRepository {

    private final UserDataMapper userDataMapper;
    private final UserJpaRepository userJpaRepository;
    private final UserWalletService userWalletService;
    private final Cache cache;

    public UserRepositoryImpl(UserDataMapper userDataMapper, UserJpaRepository userJpaRepository, UserWalletService userWalletService, Cache cache) {
        this.userDataMapper = userDataMapper;
        this.userJpaRepository = userJpaRepository;
        this.userWalletService = userWalletService;
        this.cache = cache;
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

        if (fromCache.isPresent()) {
            log.info("from cache: {}", fromCache.get());
            return userDataMapper.userEntityToUserDomain(fromCache.get());
        }

        Optional<UserEntity> user = userJpaRepository.findById(id);

        if (user.isEmpty()) {
            throw new UserException("user: " + id + " not found");
        }

        cache.put(String.valueOf(id), user.get());

        return userDataMapper.userEntityToUserDomain(user.get());
    }

    @Override
    public User getFullInfoById(Long id) {

        Optional<UserEntity> user = userJpaRepository.getFullInfo(id);

        if (user.isEmpty()) {
            throw new UserException("user: " + id + " not found");
        }

        return userDataMapper.userEntityToUserDomain(user.get());
    }

}
