package com.example.demo.external.api.notification;

import com.example.demo.domain.entities.Transfer;
import com.example.demo.domain.entities.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EmailTransferNotifierServiceImpl implements TransferNotifierService {

    private final CloseableHttpClient client;

    //todo adicionar no arquivo de conf
    private final String API_URL =  "https://run.mocky.io/v3/";

    public EmailTransferNotifierServiceImpl(CloseableHttpClient client) {
        this.client = client;
    }

    @Override
    public void process(User user, Transfer transfer) {

        String transferId = "54dc2cf1-3add-45b5-b5a9-6bf7e7f1f4a6";

        HttpGet request = new HttpGet(API_URL + transferId);

        log.info("notifying user: {} about transfer value: {}", user.getId(), transfer.getAmount());


        try (ClassicHttpResponse response = client.execute(request)){

            if(HttpStatus.OK.value() == response.getCode())
                log.info("user: {} notified", user.getId());

        }catch (Exception e){
            log.error("error notifying user: {} about transfer value: ", user.getId());
        }
    }
}
