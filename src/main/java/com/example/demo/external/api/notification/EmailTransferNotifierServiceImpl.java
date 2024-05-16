package com.example.demo.external.api.notification;

import com.example.demo.core.domain.exceptions.ExternalServiceException;
import com.example.demo.core.domain.valueobjects.User;
import com.example.demo.external.api.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@ConditionalOnProperty(
        value="app.mock-services",
        havingValue = "false")
public class EmailTransferNotifierServiceImpl implements TransferNotifierService {

    private final PicPayNotificationService client;

    public EmailTransferNotifierServiceImpl(PicPayNotificationService client) {
        this.client = client;
    }

    @Override
    public void process(User user) {
        try {
            log.info("notifying user: {} new balance value: {}", user.getMail(), user.getWallet().getBalance());

            client.notifyClient();
        }catch (Exception e) {
            log.error("Error when notifing client: {}", e.getMessage(), e);
        }
    }
}
