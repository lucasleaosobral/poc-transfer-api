package com.example.demo.external.api.validators;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@ConditionalOnProperty(
        value="app.mock-services",
        havingValue = "true")
public class MockTransferValidatorImpl implements TransferValidatorService {
    @Override
    public boolean validate(UUID transferId) {
        return true;
    }
}
