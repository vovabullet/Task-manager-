package com.example.taskproject.controllers;

import com.example.taskproject.services.CommentService;
import com.example.taskproject.services.DTO.TaskDto;
import com.example.taskproject.services.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class TaskControllerTest {

    private MockMvc mockMvc;
    private TaskService taskService;
    private CommentService commentService;

    @BeforeEach
    void setup() {
        // Замена @MockBean: вручную создаём мок TaskService
        taskService = Mockito.mock(TaskService.class);

        // Создание объекта контроллера с замоканным сервисом
        TaskController taskController = new TaskController(taskService, commentService);

        // Инициализация MockMvc с контроллером
        mockMvc = MockMvcBuilders.standaloneSetup(taskController).build();
    }

    @Test
    void testGetTaskById() throws Exception {
        // мокирование TaskDTO
        TaskDto task = new TaskDto();
        task.setId(1L);
        task.setTitle("Test Task");
        task.setDescription("Test Description");

        // поведение сервиса
        when(taskService.getTaskById(1L)).thenReturn(task);

        // выполнение запроса
        mockMvc.perform(get("/tasks/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Test Task"))
                .andExpect(jsonPath("$.description").value("Test Description"));

        // проверка, что метод сервиса был вызван
        verify(taskService, times(1)).getTaskById(1L);
    }
}
