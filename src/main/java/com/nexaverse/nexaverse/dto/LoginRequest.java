package com.nexaverse.nexaverse.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {

    @NotBlank(message = "Username empty nahi ho sakta")
    private String username;

    @NotBlank(message = "Password empty nahi ho sakta")
    private String password;
}