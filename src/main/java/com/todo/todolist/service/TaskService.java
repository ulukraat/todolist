package com.todo.todolist.service;

import com.todo.todolist.model.Task;
import com.todo.todolist.model.TaskStatus;
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

                if(task.getDueDate() == null){
                    task.setDueDate(LocalDateTime.now().plusDays(7));//дефолт время 7 дней
                }
                task.setStatus(TaskStatus.NEW);
                taskRepository.save(task);
    }

    public void deleteTask(Long id){
            taskRepository.deleteById(id);
    }

    public void taskStatus(Task task){
        LocalDateTime dayCreated = task.getCreatedAt();
        boolean isOverDue = task.getDueDate().isAfter(dayCreated);
        if(isOverDue){
            task.setStatus(TaskStatus.OVERDUE);
        }
    }//проверка не просрочен ли таск




}
