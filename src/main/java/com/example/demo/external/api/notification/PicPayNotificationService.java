package com.example.demo.external.api.notification;


import com.example.demo.external.api.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(
        name = "transfer-notification-api",
        url = "${transfer-notification.url}")
public interface PicPayNotificationService {

    @RequestMapping(method = RequestMethod.GET, value = "")
    ApiResponse notifyClient();

}
