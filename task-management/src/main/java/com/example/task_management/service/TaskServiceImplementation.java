package com.example.task_management.service;
import com.example.task_management.dto.TaskRequest;
import com.example.task_management.dto.TaskResponse;
import com.example.task_management.entity.Task;
import com.example.task_management.entity.Users;
import com.example.task_management.repository.TaskRepository;
import com.example.task_management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskServiceImplementation implements TaskService {
    private final TaskRepository taskRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    @Override
    public List<TaskResponse> getTasks() {
        List<Task> tasks = taskRepository.findAll();
        return tasks.stream().map(task->
                 modelMapper.map(task, TaskResponse.class)
                ).toList();
    }

    @Override
    public void createTask(TaskRequest taskRequest, Long userId) {
        Task task1 = modelMapper.map(taskRequest, Task.class);
        task1.setCreatedAt(LocalDate.now());
        task1.setLastUpdatedAt(LocalDate.now());
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        task1.setUser(user);
        taskRepository.save(task1);
    }

    @Override
    public void updateTask(Long id, TaskRequest taskRequest, Long userId) {

        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));
        if(!Objects.equals(task.getUser().getId(), user.getId())){
          throw new Error("User is not valid to update");
        }
        modelMapper.map(taskRequest, task);
        task.setLastUpdatedAt(LocalDate.now());
        taskRepository.save(task);
    }
}
