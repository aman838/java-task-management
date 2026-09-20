package com.example.task_management.dto;

import com.example.task_management.enums.Priority;
import com.example.task_management.enums.TaskStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@RequiredArgsConstructor
@Getter
@Setter
public class TaskResponse {
    private Long id;
    private String  title;
    private String description;
    private TaskStatus status;
    private Priority priority;
    private LocalDate createdAt;
    private LocalDate lastUpdatedAt;
}
