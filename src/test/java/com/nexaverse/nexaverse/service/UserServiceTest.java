package com.nexaverse.nexaverse.service;

import com.nexaverse.nexaverse.dto.UserDTO;
import com.nexaverse.nexaverse.entity.User;
import com.nexaverse.nexaverse.exception.DuplicateResourceException;
import com.nexaverse.nexaverse.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private UserDTO userDTO;
    private User user;

    @BeforeEach
    void setUp() {
        userDTO = new UserDTO();
        userDTO.setUsername("yash123");
        userDTO.setEmail("yash@gmail.com");
        userDTO.setPassword("secret123");
        userDTO.setAvatarName("DragonSlayer");

        user = new User();
        user.setId(1L);
        user.setUsername("yash123");
        user.setEmail("yash@gmail.com");
        user.setPassword("$2a$10$hashedpassword");
        user.setAvatarName("DragonSlayer");
    }

    @Test
    void createUser_Success() {
        // Arrange
        when(userRepository.existsByUsername("yash123")).thenReturn(false);
        when(userRepository.existsByEmail("yash@gmail.com")).thenReturn(false);
        when(passwordEncoder.encode("secret123")).thenReturn("$2a$10$hashedpassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Act
        User result = userService.createUser(userDTO);

        // Assert
        assertNotNull(result);
        assertEquals("yash123", result.getUsername());
        assertEquals("yash@gmail.com", result.getEmail());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void createUser_DuplicateUsername_ThrowsException() {
        // Arrange
        when(userRepository.existsByUsername("yash123")).thenReturn(true);

        // Act & Assert
        assertThrows(DuplicateResourceException.class,
                () -> userService.createUser(userDTO));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void createUser_DuplicateEmail_ThrowsException() {
        // Arrange
        when(userRepository.existsByUsername("yash123")).thenReturn(false);
        when(userRepository.existsByEmail("yash@gmail.com")).thenReturn(true);

        // Act & Assert
        assertThrows(DuplicateResourceException.class,
                () -> userService.createUser(userDTO));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void getUserById_Success() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(user));

        // Act
        User result = userService.getUserById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("yash123", result.getUsername());
    }

    @Test
    void getUserById_NotFound_ThrowsException() {
        // Arrange
        when(userRepository.findById(999L))
                .thenReturn(java.util.Optional.empty());

        // Act & Assert
        assertThrows(Exception.class,
                () -> userService.getUserById(999L));
    }
}