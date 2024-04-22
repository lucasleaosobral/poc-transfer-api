package com.example.demo.domain.events;

import com.example.demo.domain.entities.Transfer;
import com.example.demo.domain.entities.User;
import org.springframework.context.ApplicationEvent;

public class TransferEvent extends ApplicationEvent {

    private final User user;
    private final Transfer transfer;

    public TransferEvent(Object source, User user, Transfer transfer) {
        super(source);
        this.user = user;
        this.transfer = transfer;
    }

    public User getUser() {
        return user;
    }

    public Transfer getTransfer() {
        return transfer;
    }
}
