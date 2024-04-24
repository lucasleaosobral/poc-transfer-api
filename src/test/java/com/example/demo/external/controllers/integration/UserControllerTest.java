package com.example.demo.external.controllers.integration;

import com.example.demo.data.user.UserRepository;
import com.example.demo.external.inputs.CreateUserCommand;
import com.google.gson.Gson;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @LocalServerPort
    private Integer port;

    @Autowired
    UserRepository userRepository;

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:15-alpine"
    ).withInitScript("scripts.sql");

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Test
    public void createUserTest() throws Exception {
        CreateUserCommand createUserCommand = CreateUserCommand
                .builder()
                .name("teste")
                .document("39962054812")
                .email("teste")
                .password("teste")
                .build();


        Gson gson = new Gson();
        String json = gson.toJson(createUserCommand);

        mockMvc.perform(
                post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk()
                );
    }

    @Test
    public void shouldNotCreateUserWithUsedEmail() throws Exception {
        CreateUserCommand createUserCommand = CreateUserCommand
                .builder()
                .name("teste")
                .document("39962054817")
                .email("teste1234567")
                .password("teste")
                .build();

        userRepository.handleCreateUser(createUserCommand);

        Gson gson = new Gson();
        String json = gson.toJson(createUserCommand);

        mockMvc.perform(
                        post("/user")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                .andExpect(status().is4xxClientError()
                );
    }

    @Test
    public void shouldNotCreateUserWithInvalidDocument() throws Exception {
        CreateUserCommand createUserCommand = CreateUserCommand
                .builder()
                .name("teste")
                .document("")
                .email("teste")
                .password("teste")
                .build();

        Gson gson = new Gson();
        String json = gson.toJson(createUserCommand);

        mockMvc.perform(
                        post("/user")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                .andExpect(status().is4xxClientError()
                );
    }

    @Test
    public void shouldNotCreateUserWithInvalidEmail() throws Exception {
        CreateUserCommand createUserCommand = CreateUserCommand
                .builder()
                .name("teste")
                .document("39962054812")
                .email("")
                .password("teste")
                .build();

        Gson gson = new Gson();
        String json = gson.toJson(createUserCommand);

        mockMvc.perform(
                        post("/user")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                .andExpect(status().is4xxClientError()
                );
    }

    @Test
    public void shouldNotCreateUserWithInvalidPassword() throws Exception {
        CreateUserCommand createUserCommand = CreateUserCommand
                .builder()
                .name("teste")
                .document("39962054812")
                .email("123")
                .password(null)
                .build();

        Gson gson = new Gson();
        String json = gson.toJson(createUserCommand);

        mockMvc.perform(
                        post("/user")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                .andExpect(status().is4xxClientError()
                );
    }
}
