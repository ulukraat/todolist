package com.todo.todolist.service;

import com.todo.todolist.model.Task;
import com.todo.todolist.model.TaskStatus;
import com.todo.todolist.repository.TaskRepository;


import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;

    public List<Task> getAllTasks(){
        return taskRepository.findAll();
    }
    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Задача не найдена"));
    }



    public void createTasks(Task task){
                task.setCreatedAt(LocalDate.now());

                if (task.getDueDate().isBefore(task.getCreatedAt())){
                    throw new RuntimeException("Срок задачи не может быть позже настоящего");
                }
                task.setStatus(TaskStatus.NEW);
                taskRepository.save(task);
    }

    public void deleteTask(Long id){
            taskRepository.deleteById(id);
    }

    public void taskStatus(Task task) {
        LocalDate dayCreated = task.getCreatedAt();
        boolean isOverDue = task.getDueDate().isBefore(dayCreated);
        if (isOverDue) {
            task.setStatus(TaskStatus.OVERDUE);//проверка не просрочен ли таск
            taskRepository.save(task);
        } else if (task.getStatus() == TaskStatus.NEW) {
            task.setStatus(TaskStatus.IN_PROGRESS);
            taskRepository.save(task);
        }
    }

    public void isInProgress(Task task) {
        if (task.getStatus() == TaskStatus.IN_PROGRESS) {
            task.setStatus(TaskStatus.COMPLETED);
            taskRepository.save(task);
        }else if (task.getStatus() == TaskStatus.NEW) {
            return;
        }
    }

    public void redactTask(Task task) {
        task.setCreatedAt(LocalDate.now());

        taskRepository.save(task);
    }





}
