package com.example.demo.external.api.notification;

import com.example.demo.core.domain.valueobjects.User;

public interface TransferNotifierService {
    void process(User user);
}
