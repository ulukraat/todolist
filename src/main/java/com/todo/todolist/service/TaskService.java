package com.todo.todolist.service;

import com.todo.todolist.model.Task;
import com.todo.todolist.repository.TaskRepository;


import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;

    public List<Task> getAllTasks(){
        return taskRepository.findAll();
    }

    public void createTasks(Task task){
                task.setCreatedAt(LocalDateTime.now());
                taskRepository.save(task);
    }
    public void deleteTask(Long id){
            taskRepository.deleteById(id);
    }


}
