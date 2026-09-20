package com.example.task_management.controller;

import com.example.task_management.dto.TaskRequest;
import com.example.task_management.dto.TaskResponse;
import com.example.task_management.service.TaskServiceImplementation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TaskController {

    private final TaskServiceImplementation taskService;

    @GetMapping("/api/tasks")
    public List<TaskResponse> getTasks(){
        return taskService.getTasks();
    }

    @PostMapping("/api/task")
    public ResponseEntity<String> createTask(@Valid  @RequestBody TaskRequest taskRequest, @AuthenticationPrincipal Jwt jwt){
        Long userId = jwt.getClaim("userId");
        taskService.createTask(taskRequest,userId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Task has been created successfully");
    }

    @PutMapping("/api/task/{id}")
    public ResponseEntity<String> updateTask(
            @PathVariable Long id,
            @Valid @RequestBody TaskRequest taskRequest,
            @AuthenticationPrincipal Jwt jwt
            ) {
        Long userId = jwt.getClaim("userId");
        taskService.updateTask(id, taskRequest, userId);
        return ResponseEntity.ok("Task has been updated successfully");
    }

}
