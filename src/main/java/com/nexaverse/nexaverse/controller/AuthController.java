package com.nexaverse.nexaverse.controller;

import com.nexaverse.nexaverse.dto.ApiResponse;
import com.nexaverse.nexaverse.dto.AuthResponse;
import com.nexaverse.nexaverse.dto.LoginRequest;
import com.nexaverse.nexaverse.dto.UserDTO;
import com.nexaverse.nexaverse.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Authentication", description = "Login and Register APIs")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(@Valid @RequestBody UserDTO dto) {
        AuthResponse response = authService.register(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Registered!", response));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(ApiResponse.success("Logged in!", response));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<String>> me() {
        return ResponseEntity.ok(ApiResponse.success("Token valid!", "You are authenticated!"));
    }
}