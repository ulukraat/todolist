package com.todo.todo.controllerTest;

import com.todo.todo.controller.TaskController;
import com.todo.todo.model.Task;
import com.todo.todo.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(TaskController.class)
class TaskControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TaskService taskService;
    private List<Task> tasks;

    @BeforeEach
    void setUp() {
        Task task = new Task("bug fix", LocalDateTime.of(2025,12,06,20,0));
        task.setIsCompleted(false); // или true
        tasks = List.of(task);
    }


    @Test
    void shouldReturnAllTasks() throws Exception {
        when(taskService.getTasksByUserUsername("uluk")).thenReturn(tasks);

        mockMvc.perform(get("/tasks").principal(() -> "uluk"))
                .andExpect(status().isOk())
                .andExpect(view().name("task-list"))
                .andExpect(model().attributeExists("tasks"));


    }
}
