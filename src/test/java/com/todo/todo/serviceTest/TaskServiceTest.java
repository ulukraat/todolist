package com.todo.todo.serviceTest;

import com.todo.todo.model.Task;
import com.todo.todo.model.User;
import com.todo.todo.repository.TaskRepository;
import com.todo.todo.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.testng.AssertJUnit.assertEquals;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    private Task task1;
    private Task task2;
    private Task task3;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(10L);
        user.setUsername("uluk");

        task1 = new Task();
        task1.setId(1L);
        task1.setTitle("fix bug");
        task1.setStartDate(LocalDateTime.of(2025,9,5,14,0));
        task1.setDueDate(task1.getStartDate().plusDays(1));
        task1.setIsCompleted(false);
        task1.setUser(user);

        task2 = new Task();
        task2.setId(2L);
        task2.setTitle("push up 100 times");
        task2.setStartDate(LocalDateTime.of(2025,9,5,20,0));
        task2.setDueDate(task2.getStartDate().plusDays(1));
        task2.setIsCompleted(true);
        task2.setUser(user);

        task3 = new Task();
        task3.setId(3L);
        task3.setTitle("kiss my cat 100 times");
        task3.setStartDate(LocalDateTime.of(2025,9,4,20,0));
        task3.setDueDate(LocalDateTime.of(2025,9,2,20,0));
    }

    @Test
    void shouldCreateTask() {
        when(taskRepository.save(any(Task.class))).thenReturn(task1);

        Task result = taskService.saveTask(task1);

        assertEquals("fix bug", result.getTitle());
        assertEquals(LocalDateTime.of(2025,9,5,14,0), result.getStartDate());
        assertEquals(LocalDateTime.of(2025,9,6,14,0), result.getDueDate());
        assertEquals(Boolean.FALSE,result.getIsCompleted());
    }

    @Test
    void shouldGetTaskByUserName(){
        when(taskRepository.findTaskByUserUsername("uluk")).thenReturn(List.of(task1,task2));

        List<Task> result = taskService.getTasksByUserUsername("uluk");
        assertEquals(2, result.size());
        assertEquals("fix bug", result.get(0).getTitle());
        assertEquals(LocalDateTime.of(2025,9,5,14,0), result.get(0).getStartDate());
        assertEquals(LocalDateTime.of(2025,9,6,14,0), result.get(0).getDueDate());
        assertEquals(Boolean.FALSE,result.get(0).getIsCompleted());
        assertEquals("uluk", result.get(0).getUser().getUsername());
        assertEquals("push up 100 times", result.get(1).getTitle());
        assertEquals(LocalDateTime.of(2025,9,5,20,0), result.get(1).getStartDate());
        assertEquals(LocalDateTime.of(2025,9,6,20,0), result.get(1).getDueDate());
        assertEquals(Boolean.TRUE,result.get(1).getIsCompleted());
        assertEquals("uluk", result.get(1).getUser().getUsername());
    }
    @Test
    void shouldDeleteTaskById(){
        taskService.deleteTaskById(1L);

        verify(taskRepository).deleteById(1L);
    }

    @Test
    void shouldUpdateTask(){
            when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> invocation.getArgument(0));

            task1.setTitle("updated title");
            task1.setIsCompleted(true);

            Task result = taskService.saveTask(task1);

            assertEquals("updated title", result.getTitle());
            assertEquals(Boolean.TRUE, result.getIsCompleted());
            verify(taskRepository).save(task1);
        }

    @Test
    void shouldThrowExceptionIfDueDateIsBeforeStartDate(){
        assertThrows(IllegalArgumentException.class, () -> taskService.saveTask(task3));
        verify(taskRepository, never()).save(any(Task.class));
    }
    }
