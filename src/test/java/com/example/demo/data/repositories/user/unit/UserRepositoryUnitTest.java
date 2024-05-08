package com.example.demo.data.repositories.user.unit;


import com.example.demo.core.model.entities.user.UserEntity;
import com.example.demo.core.model.entities.wallet.UserWalletEntity;
import com.example.demo.core.model.repositories.user.UserJpaRepository;
import com.example.demo.core.model.repositories.user.UserRepository;
import com.example.demo.core.domain.valueobjects.AccountType;
import com.example.demo.core.domain.valueobjects.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;


import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableCaching
@ImportAutoConfiguration(classes = {
        CacheAutoConfiguration.class,
        RedisAutoConfiguration.class
})
public class UserRepositoryUnitTest {

    @MockBean
    UserJpaRepository userJpaRepository;

    @Autowired
    UserRepository userRepository;

    static GenericContainer<?> redis =
            new GenericContainer<>(DockerImageName.parse("redis:5.0.3-alpine"))
                    .withExposedPorts(6379);

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:15-alpine"
    ).withInitScript("scripts.sql");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);

        registry.add("spring.data.redis.host", redis::getHost);
        registry.add("spring.data.redis.port", () -> redis.getMappedPort(6379).toString());

    }

    @BeforeAll
    static void beforeAll() {
        redis.start();
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        redis.stop();
        postgres.stop();
    }

    @Test
    public void shouldRetrieveUserFromCacheTest() {

        User user = User.builder()
                .id(1l)
                .build();

        UserWalletEntity userWalletEntity = UserWalletEntity.builder()
                .id(UUID.randomUUID())
                .balance(BigDecimal.ZERO)
                .build();

        UserEntity userEntity = UserEntity.builder()
                .id(1l)
                .name("teste")
                .email("teste")
                .accountType(String.valueOf(AccountType.USER))
                .document("39962054811")
                .password("123")
                .wallet(userWalletEntity)
                .build();

        Mockito.when(userJpaRepository.findById(user.getId())).thenReturn(Optional.of(userEntity));

        User notFromCache = userRepository.getById(user.getId());
        User userFromCache = userRepository.getById(user.getId());

        assertThat(notFromCache).isEqualTo(notFromCache);
        assertThat(userFromCache).isEqualTo(userFromCache);

        verify(userJpaRepository, times(1)).findById(user.getId());

    }


}
