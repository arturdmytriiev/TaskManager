package org.example.taskmanager;

import org.example.taskmanager.controller.TaskController;
import org.example.taskmanager.dto.TaskDTO;
import org.example.taskmanager.entity.Task;
import org.example.taskmanager.entity.enums.TaskPriority;
import org.example.taskmanager.entity.enums.TaskStatus;
import org.example.taskmanager.service.TaskService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
@WebMvcTest(TaskController.class)
class TaskControllerTest {
   @Autowired
    private MockMvc mockMvc;
   @Mock
   private TaskService taskService;
   private Task task;
   private TaskDTO taskDTO;

    @BeforeEach
    void setUp() {
        task = new Task(1L, "Test Task", "Test Description", TaskStatus.IN_PROGRESS, TaskPriority.MEDIUM, null);
        taskDTO = new TaskDTO("Test Task", "Test Description", TaskStatus.IN_PROGRESS, TaskPriority.MEDIUM);
    }
    //TODO write MVC tests

}
