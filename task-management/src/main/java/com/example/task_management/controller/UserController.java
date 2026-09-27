package com.example.task_management.controller;

import com.example.task_management.dto.TaskRequest;
import com.example.task_management.dto.TaskResponse;
//import com.example.task_management.service.JwtService;
import com.example.task_management.dto.User.UserRequestCityNameDto;
import com.example.task_management.dto.User.UserResponse;
import com.example.task_management.service.userservice.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("api/user/tasks")
    public List<TaskResponse> getUserTasks(@AuthenticationPrincipal Jwt jwt){
        Long userId = jwt.getClaim("userId");
        if(userId==null){
             throw new Error("UserId is invalid");
        }
    return userService.getUserTasks(userId);
    }

    @PutMapping("api/user/update-city")
    public ResponseEntity<String> updateCityName(
            @Valid @RequestBody UserRequestCityNameDto requestBody,
            @AuthenticationPrincipal Jwt jwt
    ) {
        Long userId = jwt.getClaim("userId");
        userService.updateUserCityName(requestBody, userId);
        return ResponseEntity.ok("user city has been updated successfully");
    }

    @GetMapping("api/user/user-information")
    public Mono<UserResponse> getUserInfo(@AuthenticationPrincipal Jwt jwt){
        Long userId = jwt.getClaim("userId");
        return userService.getUserInformation(userId);
    }
}
