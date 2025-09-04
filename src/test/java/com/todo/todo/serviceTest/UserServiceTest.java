package com.todo.todo.serviceTest;

import com.todo.todo.model.User;
import com.todo.todo.repository.UserRepository;
import com.todo.todo.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.AssertJUnit.assertEquals;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setUsername("uluk");
        user.setPassword("securepass");
    }
    @Test
    void shouldReturnUserDetailsWhenUserExists() {
        when(userRepository.findByUsername("uluk")).thenReturn(Optional.of(user));

        UserDetails result = userService.loadUserByUsername("uluk");

        assertEquals("uluk", result.getUsername());
        assertEquals("securepass", result.getPassword());
        verify(userRepository).findByUsername("uluk");
    }
    @Test
    void shouldReturnUserDetailsWhenUserDoesNotExist() {
        when(userRepository.findByUsername("unknown")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("unknown"));
        verify(userRepository).findByUsername("unknown");
    }
}
