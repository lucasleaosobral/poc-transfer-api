package com.example.demo.external.api.validators;

import com.example.demo.domain.exceptions.ExternalServiceException;
import lombok.extern.log4j.Log4j2;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
@Log4j2
public class PicPayTransferValidatorImpl implements TransferValidatorService{

    private final CloseableHttpClient client;

    //todo adicionar no arquivo de conf
    private final String API_URL =  "https://run.mocky.io/v3/";

    public PicPayTransferValidatorImpl(CloseableHttpClient client) {
        this.client = client;
    }

    @Override
    public boolean validate(UUID transferId) {

        HttpGet request = new HttpGet(API_URL + transferId);

        try (ClassicHttpResponse response = client.execute(request)){

            if(HttpStatus.OK.value() == response.getCode())
                return true;

        }catch (Exception e){
            log.info(e.getMessage());
            throw new ExternalServiceException("Error while validating transfer " + transferId);
        }
        return false;
    }
}
