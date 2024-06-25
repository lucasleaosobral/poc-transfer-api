package com.example.demo.core.model.repositories.user;

import com.example.demo.core.model.entities.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {


    @Query("SELECT u FROM UserEntity u JOIN FETCH u.wallet WHERE u.id = :id")
    Optional<UserEntity> getFullInfo(Long id);
}
