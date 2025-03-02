package org.example.taskmanager.service;

import lombok.RequiredArgsConstructor;
import org.example.taskmanager.dto.LoginDTO;
import org.example.taskmanager.entity.User;
import org.example.taskmanager.repository.UserRepository;
import org.example.taskmanager.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public String login(LoginDTO loginDTO) {
        User user = userRepository.findByUsername(loginDTO.getUsername())
                .orElseThrow(()-> new RuntimeException("User not found"));

        if(!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new RuntimeException("Wrong password");
        }

        return jwtUtil.generateToken(user.getUsername());
    }
}
