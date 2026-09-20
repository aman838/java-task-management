package com.example.task_management.service.userservice;

import com.example.task_management.dto.TaskResponse;
import com.example.task_management.dto.User.LoginRequest;
import com.example.task_management.dto.User.UserRequest;

import java.util.List;

public interface UserService {
     void createUser(UserRequest user);
     String login(LoginRequest login);
     List<TaskResponse> getUserTasks(Long id);
}
