package com.nexaverse.nexaverse.service;

import com.nexaverse.nexaverse.config.JwtUtil;
import com.nexaverse.nexaverse.dto.AuthResponse;
import com.nexaverse.nexaverse.dto.LoginRequest;
import com.nexaverse.nexaverse.dto.UserDTO;
import com.nexaverse.nexaverse.entity.User;
import com.nexaverse.nexaverse.exception.BadRequestException;
import com.nexaverse.nexaverse.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    // Register
    public AuthResponse register(UserDTO dto) {
        User user = userService.createUser(dto);
        String token = jwtUtil.generateToken(user.getUsername());
        return new AuthResponse(token, user.getUsername(), "Registration successful!");
    }

    // Login
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new BadRequestException("Invalid username or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadRequestException("Invalid username or password");
        }

        String token = jwtUtil.generateToken(user.getUsername());
        return new AuthResponse(token, user.getUsername(), "Login successful!");
    }
}