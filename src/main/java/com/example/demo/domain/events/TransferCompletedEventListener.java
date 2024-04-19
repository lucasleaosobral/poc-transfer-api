package com.example.demo.domain.events;


import com.example.demo.external.api.notification.TransferNotifierService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@Slf4j
public class TransferCompletedEventListener {

    private final TransferNotifierService transferNotifierService;

    public TransferCompletedEventListener(TransferNotifierService transferNotifierService) {
        this.transferNotifierService = transferNotifierService;
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMPLETION)
    public void handleEvent(CustomEvent customEvent) {
      log.info("starting user notification");
      transferNotifierService.process(customEvent.getUser(),customEvent.getTransfer());
    }
}
