package org.example.taskmanager;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.taskmanager.entity.User;
import org.example.taskmanager.repository.UserRepository;
import org.example.taskmanager.security.JwtFilter;
import org.example.taskmanager.security.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class JwtFilerChainTest {
    @Mock
    private JwtUtil jwtUtil;
    @Mock
    private UserRepository userRepository;
    @Mock
    private FilterChain filterChain;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private UserDetails userDetails;
    @InjectMocks
    private JwtFilter jwtFilter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDoFilterInternal_Success() throws ServletException, IOException {
        when(request.getHeader("Authorization")).thenReturn("Bearer validToken");
        when(jwtUtil.extractUsername("validToken")).thenReturn("testUser");
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of((User) userDetails));
        when(jwtUtil.validateToken("validToken",userDetails)).thenReturn(true);

        jwtFilter.doFilter(request,response,filterChain);
        verify(filterChain, times(1)).doFilter(request, response);
    }
    @Test
    void testDoFilterInternal_Failure() throws ServletException, IOException {
        when(request.getHeader("Authorization")).thenReturn("Bearer invalidToken");
        when(jwtUtil.extractUsername("invalidToken")).thenReturn("testUser");
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of((User) userDetails));
        when(jwtUtil.validateToken("invalidToken",userDetails)).thenReturn(false);

        jwtFilter.doFilter(request,response,filterChain);

        verify(filterChain, times(1)).doFilter(request, response);
    }
}
