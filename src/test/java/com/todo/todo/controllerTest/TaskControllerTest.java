package com.todo.todo.controllerTest;

import com.todo.todo.controller.TaskController;
import com.todo.todo.model.Task;
import com.todo.todo.model.User;
import com.todo.todo.service.TaskService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(TaskController.class)
class TaskControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TaskService taskService;
    private List<Task> tasks;
    private User user;

    @BeforeEach
    void setUp() {
        Task task = new Task("bug fix", LocalDateTime.of(2025,12,06,20,0));
        task.setIsCompleted(false); //
        tasks = List.of(task);

        User user = new User();
        user.setId(1L);
    }


    @Test
    void shouldReturnAllTasks() throws Exception {
        when(taskService.getTasksByUserUsername("uluk")).thenReturn(tasks);

        mockMvc.perform(get("/tasks").principal(() -> "uluk"))
                .andExpect(status().isOk())
                .andExpect(view().name("task-list"))
                .andExpect(model().attributeExists("tasks"));
    }

    @Test
    void shouldAddNewTask() throws Exception {
        mockMvc.perform(get("/tasks/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("task-form"))
                .andExpect(model().attributeExists("task"))
                .andExpect(model().attribute("task", Matchers.instanceOf(Task.class)));
    }
    @Test
    void shouldSaveTask() throws Exception {
        Task task = new Task("bug fix", LocalDateTime.of(2025, 12, 6, 20, 0));
        task.setIsCompleted(false);
        task.setId(null); // новая задача

        when(taskService.saveTask(any(Task.class))).thenReturn(task);

        mockMvc.perform(post("/tasks/save")
                        .principal(() -> "user") // эмуляция @AuthenticationPrincipal
                        .flashAttr("task", task)) // передаём одиночный Task
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/tasks"));

        verify(taskService).saveTask(any(Task.class));
    }
    @Test
    void shouldDeleteTaskAndRedirect() throws Exception {
        Long taskId = 42L;


        doNothing().when(taskService).deleteTaskById(taskId);

        mockMvc.perform(get("/tasks/delete/{id}", taskId))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/tasks"));

        verify(taskService).deleteTaskById(taskId);
    }

    @Test
    void shouldEditTaskAndReturnFormView() throws Exception {
        Long taskId = 42L;
        Task existingTask = new Task("Fix bug", LocalDateTime.of(2025, 12, 6, 20, 0));
        existingTask.setId(taskId);

        when(taskService.getTaskById(taskId)).thenReturn(existingTask);

        mockMvc.perform(get("/tasks/edit/{id}", taskId))
                .andExpect(status().isOk())
                .andExpect(view().name("task-form"))
                .andExpect(model().attributeExists("task"))
                .andExpect(model().attribute("task", Matchers.instanceOf(Task.class)))
                .andExpect(model().attribute("task", Matchers.hasProperty("id", Matchers.equalTo(taskId))));

        verify(taskService).getTaskById(taskId);
    }

}
