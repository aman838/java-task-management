package com.example.task_management.service;

import com.example.task_management.dto.TaskRequest;
import com.example.task_management.dto.TaskResponse;
import java.util.List;

public interface TaskService {
     List<TaskResponse> getTasks();
     void createTask(TaskRequest task, Long UserId);
     void updateTask(Long id, TaskRequest taskRequest, Long UserId);
}
