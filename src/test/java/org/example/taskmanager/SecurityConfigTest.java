package org.example.taskmanager;

import org.example.taskmanager.config.SecurityConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.mock;

@WebMvcTest(SecurityConfig.class)
class SecurityConfigTest {
    @Autowired
    private MockMvc mvc;
    private SecurityConfig securityConfig;

    @BeforeEach
    void setUp() {
        securityConfig = new SecurityConfig(null, null, null);
    }

    @Test
    void testSecurityFilterChainConfig() throws Exception {
        HttpSecurity httpSecurity = mock(HttpSecurity.class);

        SecurityFilterChain securityFilterChain = securityConfig.securityFilterChain(httpSecurity);

        Assertions.assertNotNull(securityFilterChain);
    }

}
