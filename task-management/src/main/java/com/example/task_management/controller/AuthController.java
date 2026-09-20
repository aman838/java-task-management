package com.example.task_management.controller;

import com.example.task_management.dto.User.LoginRequest;
import com.example.task_management.dto.User.UserRequest;
import com.example.task_management.service.userservice.UserServiceImplementation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {
    private final UserServiceImplementation userServiceImplementation;

    @PostMapping("/api/signup")
    public ResponseEntity<String> Signup(@RequestBody @Valid UserRequest userRequest){
       userServiceImplementation.createUser(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("User Created Successfully!");
    }

    @PostMapping("/api/login")
    public ResponseEntity<String> Login(@RequestBody @Valid LoginRequest loginRequest){
        System.out.println(loginRequest);
        log.debug("login Request");
     String token = userServiceImplementation.login(loginRequest);
     return ResponseEntity.ok(token);
    }
}
