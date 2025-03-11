package org.example.taskmanager;


import org.example.taskmanager.dto.TaskDTO;
import org.example.taskmanager.entity.Task;
import org.example.taskmanager.entity.User;
import org.example.taskmanager.entity.enums.TaskPriority;
import org.example.taskmanager.entity.enums.TaskStatus;
import org.example.taskmanager.entity.enums.UserRole;
import org.example.taskmanager.repository.TaskRepository;
import org.example.taskmanager.repository.UserRepository;
import org.example.taskmanager.service.TaskService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;


class TaskServiceTest {
    @Mock
    private TaskRepository taskRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void testSaveTask_Sucess(){
        TaskDTO taskDTO = new TaskDTO("Test Task", "Description", TaskStatus.IN_PROGRESS, TaskPriority.HIGH);
        User user = new User(1L ,"Vitya","arturdmytriiev@gmail.com","2413127", UserRole.USER,null);
        Task task = new Task(1L , "Test Task", "Description", TaskStatus.IN_PROGRESS, TaskPriority.HIGH, user);

        Mockito.when(userRepository.findByUsername("Vitya")).thenReturn(Optional.of(user));
        Mockito.when(taskRepository.save(Mockito.any(Task.class))).thenReturn(task);

        Task savedTask   = taskService.save(taskDTO,"Vitya");
        Assertions.assertNotNull(savedTask);
        Assertions.assertEquals("Test Task" , savedTask.getTitle());
        Mockito.verify(taskRepository, Mockito.times(1)).save(Mockito.any(Task.class));
     }

     @Test
    void testFindTaskById_Found(){
        Task task = new Task(1L , "Test Task", "Description", TaskStatus.IN_PROGRESS, TaskPriority.HIGH, null);
        Mockito.when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        Optional<Task> foundTask = taskService.findById(1L);
        Assertions.assertTrue(foundTask.isPresent());
        Assertions.assertEquals("Test Task" , foundTask.get().getTitle());

     }

     @Test
     void testFindTaskById_NotFound(){
        Mockito.when(taskRepository.findById(1L)).thenReturn(Optional.empty());
        Exception exception = Assertions.assertThrows(RuntimeException.class, () -> taskService.findById(1L));
        Assertions.assertEquals("Task not found", exception.getMessage());
     }
}
