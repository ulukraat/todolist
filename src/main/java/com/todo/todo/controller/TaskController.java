package com.todo.todo.controller;

import com.todo.todo.model.Task;
import com.todo.todo.model.User;
import com.todo.todo.repository.TaskRepository;
import com.todo.todo.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @GetMapping()
    public String getTask(Model model, Principal principal) {
        String username = principal.getName();
        List<Task> tasks = taskService.getTasksByUserUsername(username);

        for (Task task : tasks) {
            System.out.println("Задача ID: " + task.getId());
            System.out.println("Название: " + task.getTitle());
            System.out.println("Начало задачи: " + task.getStartDate());
            System.out.println("Конец задачи: " + task.getDueDate());
            System.out.println("Статус задачи" +( task.getIsCompleted() ? " - выполнено" : " - еще не выполнен"));
        }
        model.addAttribute("tasks", tasks);
        return "task-list";
    }

    @GetMapping("/new")
    public String newTask(Model model) {
        model.addAttribute("task", new Task());
        return "task-form";
    }

    @PostMapping("/save")
    public String saveTask(@ModelAttribute("task") Task task, @AuthenticationPrincipal User user) {
        if (task.getId() == null) {
            task.setStartDate(LocalDateTime.now());
        } else {
            Task existing = taskService.getTaskById(task.getId());
            task.setStartDate(existing.getStartDate());
        }
        task.setUser(user);
        taskService.saveTask(task);
        return "redirect:/tasks";
    }

    @GetMapping("/delete/{id}")
    public String deleteTask(@PathVariable("id") Long id) {
        taskService.deleteTaskById(id);
        return "redirect:/tasks";
    }
    @GetMapping("/edit/{id}")
    public String editTask(@PathVariable("id") Long id, Model model) {
        Task task = taskService.getTaskById(id);
        if (task == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found");
        }
        model.addAttribute("task", task);
        return "task-form";
    }

}
