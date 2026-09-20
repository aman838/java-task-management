package com.example.task_management.entity;

import com.example.task_management.enums.Priority;
import com.example.task_management.enums.TaskStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@ToString
@Getter
@Setter
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    private LocalDate createdAt;
    private LocalDate lastUpdatedAt;

    @ManyToOne
    @JoinColumn(name="user_id")
    private Users user;
}