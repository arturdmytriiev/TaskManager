package org.example.taskmanager.repository;

import org.example.taskmanager.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
        List<Task> findById(String title);
}
