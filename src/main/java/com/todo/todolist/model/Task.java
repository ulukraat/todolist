package com.todo.todolist.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table (name = "tasks")
@Data
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(name = "created")
    private LocalDateTime createdAt;//дата создания

    @Column(name = "due_date")
    private LocalDateTime dueDate;//срок выполнения

    @Enumerated(EnumType.STRING)
    private TaskStatus status = TaskStatus.NEW;


}
