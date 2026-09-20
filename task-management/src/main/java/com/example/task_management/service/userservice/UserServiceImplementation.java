package com.example.task_management.service.userservice;

import com.example.task_management.dto.TaskResponse;
import com.example.task_management.dto.User.LoginRequest;
import com.example.task_management.dto.User.UserRequest;
import com.example.task_management.entity.Task;
import com.example.task_management.entity.Users;
import com.example.task_management.repository.TaskRepository;
import com.example.task_management.repository.UserRepository;
import com.example.task_management.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImplementation implements UserService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final TaskRepository taskRepository;

    @Override
    public void createUser(UserRequest userRequest) {
        if (userRepository.existsByUsername(userRequest.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        Users user = modelMapper.map(userRequest, Users.class);
        user.setPassword(
                passwordEncoder.encode(userRequest.getPassword())
        );
        userRepository.save(user);
    }

    public String login(LoginRequest request) {

        Users user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() ->
                        new RuntimeException("Invalid username or password")
                );

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {

            throw new RuntimeException("Invalid username or password");
        }

        return jwtService.generateToken(user);
    }

    @Override
    public List<TaskResponse> getUserTasks(Long userId) {
        List<Task> tasks = taskRepository.findAllByUserId(userId);
        return tasks.stream().map(task->modelMapper.map(task, TaskResponse.class)).toList();
    }
}
