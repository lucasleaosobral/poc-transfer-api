package com.example.demo.external.controllers.mapper;

import com.example.demo.core.domain.valueobjects.User;
import com.example.demo.external.controllers.outputs.UserDTO;
import org.springframework.stereotype.Component;

@Component
public class UserOutputMapper {

    public UserDTO userDomainToUserDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .accountType(user.getAccountType().toString())
                .mail(user.getMail())
                .balance(user.getWallet().getBalance())
                .build();
    }
}
