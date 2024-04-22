package com.example.demo.domain.events;

import com.example.demo.domain.entities.User;
import com.example.demo.external.kafka.KafkaProducer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@Slf4j
public class TransferCompletedEventListener {

    private final KafkaProducer kafkaProducer;
    private final Gson gson;

    @Value("${app.kafka.topic}")
    private String mailTopic;

    public TransferCompletedEventListener(KafkaProducer kafkaProducer, ObjectMapper objectMapper, Gson gson) {
        this.kafkaProducer = kafkaProducer;
        this.gson = gson;
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMPLETION)
    public void handleEvent(TransferEvent transferEvent) {
        log.info("starting user notification");
        kafkaProducer.sendMessage(mailTopic, gson.toJson(transferEvent.getUser(), User.class));
    }
}
