package com.example.demo.external.api.validators;

import java.util.UUID;

public interface TransferValidatorService {

    boolean validate(UUID transferId);
}
