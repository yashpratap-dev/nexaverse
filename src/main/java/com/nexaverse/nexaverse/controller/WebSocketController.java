package com.nexaverse.nexaverse.controller;

import com.nexaverse.nexaverse.dto.WebSocketMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
@Slf4j
public class WebSocketController {

    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/avatar.move")
    @SendTo("/topic/world/{worldId}")
    public WebSocketMessage handleAvatarMove(WebSocketMessage message) {
        message.setType("AVATAR_MOVED");
        message.setTimestamp(LocalDateTime.now().toString());
        log.info("Avatar {} moved to ({}, {})",
                message.getAvatarName(), message.getPositionX(), message.getPositionY());
        return message;
    }

    @MessageMapping("/avatar.join")
    public void handleAvatarJoin(WebSocketMessage message) {
        message.setType("AVATAR_JOINED");
        message.setTimestamp(LocalDateTime.now().toString());
        log.info("Avatar {} joined world {}", message.getAvatarName(), message.getWorldId());
        messagingTemplate.convertAndSend(
                "/topic/world/" + message.getWorldId(), message);
    }

    @MessageMapping("/avatar.leave")
    public void handleAvatarLeave(WebSocketMessage message) {
        message.setType("AVATAR_LEFT");
        message.setTimestamp(LocalDateTime.now().toString());
        log.info("Avatar {} left world {}", message.getAvatarName(), message.getWorldId());
        messagingTemplate.convertAndSend(
                "/topic/world/" + message.getWorldId(), message);
    }

    @MessageMapping("/chat.send")
    public void handleChatMessage(WebSocketMessage message) {
        message.setType("CHAT_MESSAGE");
        message.setTimestamp(LocalDateTime.now().toString());
        log.info("Chat from {}: {}", message.getAvatarName(), message.getContent());
        messagingTemplate.convertAndSend(
                "/topic/world/" + message.getWorldId() + "/chat", message);
    }
}