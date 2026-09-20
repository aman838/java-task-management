package com.example.task_management.controller;

import com.example.task_management.dto.TaskResponse;
//import com.example.task_management.service.JwtService;
import com.example.task_management.service.userservice.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
