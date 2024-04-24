package com.example.demo.external.controllers.integration;


import com.example.demo.data.repositories.WalletRepository;
import com.example.demo.data.user.UserRepository;
import com.example.demo.domain.entities.User;
import com.example.demo.external.inputs.CreateUserCommand;
import com.example.demo.external.inputs.TransferAmountCommand;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.google.gson.Gson;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import java.math.BigDecimal;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@WireMockTest(httpPort = 8089)
public class TransferControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WalletRepository walletRepository;


    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:15-alpine"
    ).withInitScript("scripts.sql");


    static final KafkaContainer kafka = new KafkaContainer(
            DockerImageName.parse("confluentinc/cp-kafka:latest"));


    @BeforeAll
    static void beforeAll() {
        postgres.start();
        kafka.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
        kafka.stop();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);

        registry.add("spring.kafka.producer.bootstrap-servers", kafka::getBootstrapServers);
        registry.add("spring.kafka.consumer.bootstrap-servers", kafka::getBootstrapServers);

    }

    @Test
    public void shouldNotTransferWithoutBalanceTest() throws Exception {

        CreateUserCommand user = CreateUserCommand
                .builder()
                .name("teste1")
                .document("39962054812")
                .email("teste123123123")
                .password("teste")
                .build();

        CreateUserCommand store = CreateUserCommand
                .builder()
                .name("teste2")
                .document("39962054812111")
                .email("teste12312312312312312")
                .password("teste")
                .build();

        Long userId = userRepository.handleCreateUser(user);
        Long storeId = userRepository.handleCreateUser(store);

        TransferAmountCommand transferAmountCommand = TransferAmountCommand.builder()
                .payee(storeId)
                .payer(userId)
                .value(100.00)
                .build();

        Gson gson = new Gson();
        String json = gson.toJson(transferAmountCommand);

        mockMvc.perform(
                        post("/transfer")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                .andExpect(status().is4xxClientError())
                .andExpect(content().string("Not enough balance."));
    }

    @Test
    public void shouldNotTransferFromStoreAccountTest() throws Exception {

        CreateUserCommand user = CreateUserCommand
                .builder()
                .name("teste")
                .document("39962054813")
                .email("teste45634564356")
                .password("teste")
                .build();

        CreateUserCommand store = CreateUserCommand
                .builder()
                .name("teste")
                .document("39962054812113")
                .email("teste1234675645845678")
                .password("teste")
                .build();

        Long userId = userRepository.handleCreateUser(user);
        Long storeId = userRepository.handleCreateUser(store);

        TransferAmountCommand transferAmountCommand = TransferAmountCommand.builder()
                .payee(userId)
                .payer(storeId)
                .value(100.00)
                .build();

        Gson gson = new Gson();
        String json = gson.toJson(transferAmountCommand);

        mockMvc.perform(
                        post("/transfer")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                .andExpect(status().is4xxClientError())
                .andExpect(content().string("Stores can't transfer money."));
    }

    @Test
    public void shouldNotTransferFromSameAccountTest() throws Exception {

        CreateUserCommand user = CreateUserCommand
                .builder()
                .name("teste")
                .document("39962054816")
                .email("teste3245234523452345")
                .password("teste")
                .build();

        Long userId = userRepository.handleCreateUser(user);

        TransferAmountCommand transferAmountCommand = TransferAmountCommand.builder()
                .payee(userId)
                .payer(userId)
                .value(100.00)
                .build();

        Gson gson = new Gson();
        String json = gson.toJson(transferAmountCommand);

        mockMvc.perform(
                        post("/transfer")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                .andExpect(status().is4xxClientError())
                .andExpect(content().string("Transfer destination is invalid."));
    }

    @Test
    public void shouldTransferValidAmountTest() throws Exception {

        CreateUserCommand user = CreateUserCommand
                .builder()
                .name("teste")
                .document("39962054818")
                .email("teste23452365245634563456")
                .password("teste")
                .build();

        CreateUserCommand store = CreateUserCommand
                .builder()
                .name("teste")
                .document("39962054812118")
                .email("teste123234523452345234555")
                .password("teste")
                .build();

        Long userId = userRepository.handleCreateUser(user);
        Long storeId = userRepository.handleCreateUser(store);

        TransferAmountCommand transferAmountCommand = TransferAmountCommand.builder()
                .payee(storeId)
                .payer(userId)
                .value(0.00)
                .build();

        Gson gson = new Gson();
        String json = gson.toJson(transferAmountCommand);

        mockMvc.perform(
                        post("/transfer")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldNotTransferToInvalidUserTest() throws Exception {

        TransferAmountCommand transferAmountCommand = TransferAmountCommand.builder()
                .payee(4L)
                .payer(1L)
                .value(10.00)
                .build();

        Gson gson = new Gson();
        String json = gson.toJson(transferAmountCommand);

        mockMvc.perform(
                        post("/transfer")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldFailTransferValidationAPI() throws Exception {

        CreateUserCommand user = CreateUserCommand
                .builder()
                .name("teste")
                .document("39962054830")
                .email("teste23452365245634563456234")
                .password("teste")
                .build();

        CreateUserCommand store = CreateUserCommand
                .builder()
                .name("teste")
                .document("39962054812145")
                .email("teste123234523452345234555243")
                .password("teste")
                .build();

        Long userId = userRepository.handleCreateUser(user);
        Long storeId = userRepository.handleCreateUser(store);

        User createdUser = userRepository.getById(userId);

        walletRepository.addAmount(createdUser.getWallet().getId(), BigDecimal.valueOf(100.00));


        TransferAmountCommand transferAmountCommand = TransferAmountCommand.builder()
                .payee(storeId)
                .payer(userId)
                .value(100.00)
                .build();

        Gson gson = new Gson();
        String json = gson.toJson(transferAmountCommand);

        stubFor(WireMock.post(urlEqualTo("/"))
                .willReturn(aResponse()
                        .withStatus(500)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"message\": \"Internal Server Error\"}")));

        mockMvc.perform(
                        post("/transfer")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                .andExpect(status().is5xxServerError());
    }


    @Test
    public void shouldNotAuthorizeTransferAPI() throws Exception {

        CreateUserCommand user = CreateUserCommand
                .builder()
                .name("teste")
                .document("39962054831")
                .email("teste234523652456345634562324")
                .password("teste")
                .build();

        CreateUserCommand store = CreateUserCommand
                .builder()
                .name("teste")
                .document("39962054812143")
                .email("teste123234523452345234551215243")
                .password("teste")
                .build();

        Long userId = userRepository.handleCreateUser(user);
        Long storeId = userRepository.handleCreateUser(store);

        User createdUser = userRepository.getById(userId);

        walletRepository.addAmount(createdUser.getWallet().getId(), BigDecimal.valueOf(100.00));


        TransferAmountCommand transferAmountCommand = TransferAmountCommand.builder()
                .payee(storeId)
                .payer(userId)
                .value(100.00)
                .build();

        Gson gson = new Gson();
        String json = gson.toJson(transferAmountCommand);

        stubFor(WireMock.post(urlEqualTo("/"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"message\": \"Não autorizado\"}")));

        mockMvc.perform(
                        post("/transfer")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldTransferAmount() throws Exception {

        CreateUserCommand user = CreateUserCommand
                .builder()
                .name("teste")
                .document("39962054819")
                .email("teste234523652456345634562")
                .password("teste")
                .build();

        CreateUserCommand store = CreateUserCommand
                .builder()
                .name("teste")
                .document("39962054812119")
                .email("teste1232345234523452345552")
                .password("teste")
                .build();

        Long userId = userRepository.handleCreateUser(user);
        Long storeId = userRepository.handleCreateUser(store);

        User createdUser = userRepository.getById(userId);

        walletRepository.addAmount(createdUser.getWallet().getId(), BigDecimal.valueOf(100.00));


        TransferAmountCommand transferAmountCommand = TransferAmountCommand.builder()
                .payee(storeId)
                .payer(userId)
                .value(100.00)
                .build();


        stubFor(WireMock.post(urlEqualTo("/"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"message\": \"Autorizado\"}")));

        Gson gson = new Gson();
        String json = gson.toJson(transferAmountCommand);

        mockMvc.perform(
                        post("/transfer")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                .andExpect(status().isOk());
    }
}
