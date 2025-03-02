package org.example.taskmanager.service;


import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.taskmanager.dto.TaskDTO;
import org.example.taskmanager.entity.Task;
import org.example.taskmanager.entity.User;
import org.example.taskmanager.repository.TaskRepository;

import org.example.taskmanager.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Transactional
    public Task save(TaskDTO taskDto , String username) {
        User user  = userRepository.findByUsername(username)
                .orElseThrow(()-> new RuntimeException("User not found"));
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

    public Optional<Task> findById(Long id) {
        return taskRepository.findById(id);
    }

    @Transactional
    public Task update(Long id, TaskDTO taskDto) {
        Task task = taskRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Task not found"));
        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setStatus(taskDto.getStatus());
        return taskRepository.save(task);
    }

    @Transactional
    public boolean delete(Long id) {
        if(taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
            return true;
        }else{
            throw new RuntimeException("Task not found");
        }
    }

}
