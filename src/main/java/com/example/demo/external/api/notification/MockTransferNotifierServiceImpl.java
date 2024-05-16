package com.example.demo.external.api.notification;

import com.example.demo.core.domain.valueobjects.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;


@Component
@Slf4j
@ConditionalOnProperty(
        value="app.mock-services",
        havingValue = "true")
public class MockTransferNotifierServiceImpl implements TransferNotifierService {

    @Override
    public void process(User user) {
        log.info("notifying user: {} new balance value: {}", user.getMail(), user.getWallet().getBalance());
    }
}
