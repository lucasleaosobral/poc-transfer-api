package com.example.demo.services;

import com.example.demo.data.user.UserRepository;
import com.example.demo.domain.entities.Transfer;
import com.example.demo.domain.entities.User;
import com.example.demo.domain.events.CustomEvent;
import com.example.demo.domain.exceptions.TransferException;
import com.example.demo.external.api.validators.TransferValidatorService;
import com.example.demo.external.inputs.TransferAmountCommand;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class TransferServiceImpl implements TransferService {


    private final UserRepository userRepository;
    private final UserWalletService userWalletService;
    private final TransferValidatorService transferValidatorService;
    private final ApplicationEventPublisher applicationEventPublisher;

    public TransferServiceImpl(UserRepository userRepository, UserWalletService userWalletService, TransferValidatorService transferValidatorService, ApplicationEventPublisher applicationEventPublisher) {
        this.userRepository = userRepository;
        this.userWalletService = userWalletService;
        this.transferValidatorService = transferValidatorService;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    @Transactional
    public void transfer(TransferAmountCommand transferAmountCommand) {
        User fromUser = userRepository.getById(transferAmountCommand.getPayer());
        User toUser = userRepository.getById(transferAmountCommand.getPayee());

        Transfer transfer = new Transfer(fromUser, toUser, transferAmountCommand.getValue());

        if(!transfer.isValid()) {
            throw new TransferException("This transfer is invalid");
        }

        //aqui entraria um transfer.getId()
        transferValidatorService.validate(UUID.fromString("5794d450-d2e2-4412-8131-73d0293ac1cc"));

        userWalletService.transferAmount(fromUser.getWallet().getId(), toUser.getWallet().getId(), transfer.getAmount());

        applicationEventPublisher.publishEvent(new CustomEvent(this, toUser, transfer));

    }
}
