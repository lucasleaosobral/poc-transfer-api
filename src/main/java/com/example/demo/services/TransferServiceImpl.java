package com.example.demo.services;

import com.example.demo.core.model.repositories.user.UserRepository;
import com.example.demo.core.domain.valueobjects.Transfer;
import com.example.demo.core.domain.valueobjects.User;
import com.example.demo.core.domain.events.TransferEvent;
import com.example.demo.core.domain.exceptions.TransferException;
import com.example.demo.external.api.validators.TransferValidatorService;
import com.example.demo.external.controllers.inputs.TransferAmountCommand;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        transfer.isValid();

        if(!transferValidatorService.validate(transfer.getId()))
                throw new TransferException("Transfer unauthorized");

        userWalletService.transferAmount(fromUser.getWallet().getId(), toUser.getWallet().getId(), transfer.getAmount());

        applicationEventPublisher.publishEvent(new TransferEvent(this, toUser, transfer));

    }
}
