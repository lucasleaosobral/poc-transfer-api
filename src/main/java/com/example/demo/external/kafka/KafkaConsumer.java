package com.example.demo.external.kafka;


import com.example.demo.domain.entities.User;
import com.example.demo.domain.events.TransferCompletedHandler;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaConsumer {

    private final TransferCompletedHandler transferCompletedHandler;
    private final Gson gson;

    public KafkaConsumer(TransferCompletedHandler transferCompletedHandler, Gson gson) {
        this.transferCompletedHandler = transferCompletedHandler;
        this.gson = gson;
    }

    @KafkaListener(topics = "mail-services", groupId = "mail-topics")
    public void listen(String message) {
        log.info(message);
        User user = gson.fromJson(message, User.class);
        transferCompletedHandler.sendMessage(user);
    }
}
