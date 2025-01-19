package com.todo.todolist.repository;

import com.todo.todolist.model.Task;
import com.todo.todolist.model.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAll();
    List<Task> findByStatus(TaskStatus status);
}
