package com.todo.todolist.controller;


import com.todo.todolist.model.Task;
import com.todo.todolist.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
@Controller
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @GetMapping("/allTask")
    public String showTasks(Model model) {
        model.addAttribute("tasks", taskService.getAllTasks());
        return "all-task";
    }

    @GetMapping("/task/create")
    public String showNewTaskForm(Model model) {
        model.addAttribute("task", new Task());
        return "task-create";
    }

    @PostMapping("/task/create")
    public String createTask(@ModelAttribute Task task) {
        taskService.createTasks(task);
        return "redirect:/allTask";
    }

    @GetMapping("/task/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return "redirect:/allTask";
    }
}


