package com.example.demo.external.api.validators;

import com.example.demo.external.api.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.UUID;

@FeignClient(
        name = "transfer-validation-api",
        url = "${transfer-validation.url}"
)
public interface PicPayTransferValidatorService {

    @RequestMapping(method = RequestMethod.POST, value = "")
    ApiResponse validate(UUID transferId);
}
