package com.example.demo.core.domain.events;

import com.example.demo.core.domain.valueobjects.User;
import com.example.demo.services.kafka.KafkaProducer;
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

    public TransferCompletedEventListener(KafkaProducer kafkaProducer, Gson gson) {
        this.kafkaProducer = kafkaProducer;
        this.gson = gson;
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMPLETION)
    public void handleEvent(TransferEvent transferEvent) {
        log.info("starting user notification");
        kafkaProducer.sendMessage(mailTopic, gson.toJson(transferEvent.getUser(), User.class));
    }
}
