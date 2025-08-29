package com.todo.todo.service;

import com.todo.todo.model.Task;
import com.todo.todo.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    public List<Task> getAllTasks(){
        List<Task> list = taskRepository.findAll();
        System.out.println("Tasks from DB: " + list);
        return list;
    }
    public Task getTaskById(Long id){
        return taskRepository.findById(id).orElse(null);
    }

    public void saveTask(Task task){
        taskRepository.save(task);
    }

    public void deleteTaskById(Long id){
        taskRepository.deleteById(id);
    }
}
