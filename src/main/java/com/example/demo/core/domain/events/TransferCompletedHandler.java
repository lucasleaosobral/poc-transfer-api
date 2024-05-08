package com.example.demo.core.domain.events;

import com.example.demo.core.domain.valueobjects.User;
import com.example.demo.external.api.notification.TransferNotifierService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TransferCompletedHandler {

    private final TransferNotifierService transferNotifierService;

    public TransferCompletedHandler(TransferNotifierService transferNotifierService) {
        this.transferNotifierService = transferNotifierService;
    }

    public void sendMessage(User user) {
        transferNotifierService.process(user);
    }
}
