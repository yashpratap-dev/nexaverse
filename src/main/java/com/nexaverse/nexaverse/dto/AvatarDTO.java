package com.nexaverse.nexaverse.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class AvatarDTO {

    private Long id;

    @NotBlank(message = "Avatar name empty nahi ho sakta")
    @Size(min = 3, max = 30, message = "Avatar name 3-30 characters ka hona chahiye")
    private String name;

    @NotBlank(message = "Avatar type empty nahi ho sakta")
    private String avatarType;

    private int level = 1;
    private double positionX = 0.0;
    private double positionY = 0.0;
    private Long userId;
}