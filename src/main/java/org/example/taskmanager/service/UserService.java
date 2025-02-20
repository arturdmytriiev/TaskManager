package org.example.taskmanager.service;

import lombok.RequiredArgsConstructor;
import org.example.taskmanager.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

}
