package com.example.task_management.dto.User;

import com.example.task_management.dto.TaskResponse;
import com.example.task_management.dto.Weather.WeatherResponse;
import com.example.task_management.entity.Task;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class UserResponse {
    private String id;
    private String username;
    private WeatherResponse weather;
    private List<TaskResponse> tasks;
}
