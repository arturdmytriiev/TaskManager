package org.example.taskmanager.service;

import lombok.RequiredArgsConstructor;
import org.example.taskmanager.dto.RegisterDTO;
import org.example.taskmanager.entity.User;
import org.example.taskmanager.entity.enums.UserRole;
import org.example.taskmanager.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User register (RegisterDTO registerDTO) {
        if(userRepository.existsByUsername(registerDTO.getUsername())) {
            throw new RuntimeException("Username is already in use");
        }

        User user = new User();
        user.setUsername(registerDTO.getUsername());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        user.setEmail(registerDTO.getEmail());
        user.setRole(UserRole.USER);
        return userRepository.save(user);
    }

}
