package org.example.taskmanager;

import org.example.taskmanager.controller.TaskController;
import org.example.taskmanager.dto.TaskDTO;
import org.example.taskmanager.entity.Task;
import org.example.taskmanager.dto.TaskDTO;
import org.example.taskmanager.entity.Task;
import org.example.taskmanager.entity.enums.TaskPriority;
import org.example.taskmanager.entity.enums.TaskStatus;
import org.example.taskmanager.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
class TaskControllerTest {
   @Autowired
    private MockMvc mockMvc;
   private TaskService taskService;
   private Task task;
   private TaskDTO taskDTO;

    @BeforeEach
    void setUp() {
        taskService = mock(TaskService.class);
        task = new Task(1L, "Test Task", "Test Description", TaskStatus.IN_PROGRESS, TaskPriority.MEDIUM, null);
        taskDTO = new TaskDTO("Test Task", "Test Description", TaskStatus.IN_PROGRESS, TaskPriority.MEDIUM);
    }

    @Test
    void createTaskTest() throws Exception {
        when(taskService.save(any(TaskDTO.class),anyString())).thenReturn(task);

        mockMvc.perform(post("/api/tasks")
                .param("username","Vitya")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                  {
                      "title": "Test Task",
                       "description": "Test Description",
                       "status": "IN_PROGRESS",
                       "priority": "MEDIUM"                                                 
                   }
                  """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is(task.getTitle())))
                .andExpect(jsonPath("$.description",is(task.getDescription())))
                .andExpect(jsonPath("$.status",is(task.getStatus().toString())))
                .andExpect(jsonPath("$.priority",is(task.getPriority().toString())));
        verify(taskService, times(1)).save(any(TaskDTO.class),anyString());
    }

    @Test
    void getAllTasksTest() throws Exception {
        List<Task> tasks = Arrays.asList(task , new Task(2L, "Another Task", "Another Desc", TaskStatus.COMPLETED, TaskPriority.HIGH, null));

        when(taskService.findAll()).thenReturn(tasks);

        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(tasks.size())))
                .andExpect(jsonPath("$[0].title", is("Test Task")))
                .andExpect(jsonPath("$[0].description", is("Test Description")))
                .andExpect(jsonPath("$[1].title", is("Another Task")))
                .andExpect(jsonPath("$[1].description", is("Another Desc")));
        verify(taskService, times(1)).findAll();
    }

    @Test
    void getTaskByIdTest() throws Exception {
        when(taskService.findById(1L)).thenReturn(Optional.of(task));

        mockMvc.perform(get("/api/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is(task.getTitle())))
                .andExpect(jsonPath("$.description", is(task.getDescription())));
        verify(taskService, times(1)).findById(1L);
    }

    @Test
    void getTaskByIdNotFoundTest() throws Exception {
        when(taskService.findById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/tasks/99"))
                .andExpect(status().isNotFound());

        verify(taskService, times(1)).findById(99L);
    }

    @Test
    void updateTaskTest() throws Exception {
        when(taskService.update(eq(1L),any(TaskDTO.class))).thenReturn(task);

        mockMvc.perform(post("/api/tasks/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                  {
                   "title": "New Test Task",
                   "description": "Test Description",
                    "status": "IN_PROGRESS",
                   "priority": "HIGH"                                                                        
                  }
                  """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is(task.getTitle())))
                .andExpect(jsonPath("$.description", is(task.getDescription())))
                .andExpect(jsonPath("$.status", is(task.getStatus().toString())))
                .andExpect(jsonPath("$.priority", is(task.getPriority().toString())));
        verify(taskService, times(1)).update(eq(1L),any(TaskDTO.class));
    }

    @Test
    void deleteTaskTest() throws Exception {
        doNothing().when(taskService).delete(1L);

        mockMvc.perform(delete("/api/tasks/1"))
                .andExpect(status().isOk());
        verify(taskService, times(1)).delete(1L);
    }

    @Test
    void deleteTaskNotFoundTest() throws Exception {
       doThrow(new RuntimeException("Task not found")).when(taskService).delete(99L);

        mockMvc.perform(delete("/api/tasks/99"))
                .andExpect(status().isNotFound());
        verify(taskService, times(1)).delete(99L);
    }

}
