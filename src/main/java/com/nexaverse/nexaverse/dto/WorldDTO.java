package com.nexaverse.nexaverse.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class WorldDTO {

    private Long id;

    @NotBlank(message = "World name empty nahi ho sakta")
    @Size(min = 3, max = 50, message = "World name 3-50 characters ka hona chahiye")
    private String name;

    @NotBlank(message = "World type empty nahi ho sakta")
    private String worldType;

    private int maxPlayers = 50;
    private boolean active = true;
}
