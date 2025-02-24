package org.example.taskmanager.service;


import lombok.RequiredArgsConstructor;
import org.example.taskmanager.dto.TaskDTO;
import org.example.taskmanager.entity.Task;
import org.example.taskmanager.entity.User;
import org.example.taskmanager.repository.TaskRepository;

import org.example.taskmanager.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Transactional
    public Task save(TaskDTO taskDto , String username) {
        User user  = userRepository.findByUsername(username)
                .orElseThrow(()-> new RuntimeException("User not founed"));
        Task task = new Task();
        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setStatus(taskDto.getStatus());
        task.setUser(user);
        return taskRepository.save(task);
    }

    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    public Task findById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Task not founed"));
    }

    public void delete(Long id) {
        if(taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
        }else{
            throw new RuntimeException("Task not founed");
        }
    }
}
