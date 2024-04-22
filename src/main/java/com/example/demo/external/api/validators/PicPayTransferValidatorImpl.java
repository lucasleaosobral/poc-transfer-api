package com.example.demo.external.api.validators;

import com.example.demo.external.api.ApiResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
@Log4j2
public class PicPayTransferValidatorImpl implements TransferValidatorService{

    private final PicPayTransferValidatorService client;

    public PicPayTransferValidatorImpl(PicPayTransferValidatorService client) {
        this.client = client;
    }

    @Override
    public boolean validate(UUID transferId) {

        ApiResponse response = client.validate(transferId);

        return response.message().equalsIgnoreCase("autorizado");
    }
}
