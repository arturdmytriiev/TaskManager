package org.example.taskmanager;

import org.example.taskmanager.dto.RegisterDTO;
import org.example.taskmanager.entity.User;
import org.example.taskmanager.entity.enums.UserRole;
import org.example.taskmanager.repository.UserRepository;
import org.example.taskmanager.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class UserServiceTest {
    @Mock
    UserRepository userRepository;
    @InjectMocks
    UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void testRegisterUser_Success(){
        RegisterDTO registerDTO = new RegisterDTO("testUser", "password", "test@example.com");
        User user = new User(1L, "testUser", "test@example.com", "password", UserRole.USER,null);

        when(userRepository.save(any(User.class))).thenReturn(user);

        User saveduser = userService.register(registerDTO);

        Assertions.assertNotNull(saveduser);
        Assertions.assertEquals("testUser", saveduser.getUsername());
        Mockito.verify(userRepository, Mockito.times(1)).save(any(User.class));
    }

    @Test
    void testFindByUsername_Success(){
        User user = new User(1L, "testUser", "test@example.com", "password", UserRole.USER,null);

        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));

        Optional<User> foundUser = userRepository.findByUsername("testUser");

        Assertions.assertTrue(foundUser.isPresent());
        Assertions.assertEquals("testUser", foundUser.get().getUsername());
    }

    @Test
    void testFindByUsername_Fail(){
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.empty());

        Optional<User> foundUser = userRepository.findByUsername("testUser");

        Assertions.assertFalse(foundUser.isPresent());

    }
}
