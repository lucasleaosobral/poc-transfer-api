package com.example.demo.external.api.notification;

import com.example.demo.domain.entities.User;

public interface TransferNotifierService {
    void process(User user);
}
