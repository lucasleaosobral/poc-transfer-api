package com.example.demo.external.controllers;


import com.example.demo.external.inputs.CreateUserCommand;
import com.example.demo.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable Long id) {
        return new ResponseEntity(null, null, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity createUser(@RequestBody @Valid CreateUserCommand createUserCommand) {

        Long id = userService.createUser(createUserCommand);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/" + id);

        return new ResponseEntity(null, headers, HttpStatus.OK);
    }
}
