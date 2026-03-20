package com.nexaverse.nexaverse.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UserDTO {

    private Long id;

    @NotBlank(message = "Username empty nahi ho sakta")
    @Size(min = 3, max = 20, message = "Username 3-20 characters ka hona chahiye")
    private String username;

    @NotBlank(message = "Email empty nahi ho sakta")
    @Email(message = "Valid email dalo")
    private String email;

    @NotBlank(message = "Password empty nahi ho sakta")
    @Size(min = 6, message = "Password minimum 6 characters ka hona chahiye")
    private String password;

    private String avatarName;
}