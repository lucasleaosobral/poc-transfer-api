package com.example.demo.external.api.notification;

import com.example.demo.domain.entities.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EmailTransferNotifierServiceImpl implements TransferNotifierService {

    private final PicPayNotificationService client;

    public EmailTransferNotifierServiceImpl(PicPayNotificationService client) {
        this.client = client;
    }

    @Override
    public void process(User user) {

        log.info("notifying user: {} new balance value: {}", user.getMail(), user.getWallet().getBalance());

        client.notifyClient();
    }
}
