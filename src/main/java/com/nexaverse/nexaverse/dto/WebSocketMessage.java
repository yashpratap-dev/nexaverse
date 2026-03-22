package com.nexaverse.nexaverse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WebSocketMessage {
    private String type;
    private Long avatarId;
    private String avatarName;
    private Long worldId;
    private Double positionX;
    private Double positionY;
    private String content;
    private String timestamp;
}