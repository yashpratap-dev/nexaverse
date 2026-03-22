package com.nexaverse.nexaverse.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class WorldEventProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendWorldJoinEvent(Long avatarId, Long worldId) {
        String message = String.format(
                "{\"event\":\"WORLD_JOIN\",\"avatarId\":%d,\"worldId\":%d}",
                avatarId, worldId);
        kafkaTemplate.send("world-events", message);
        log.info("World join event sent: {}", message);
    }

    public void sendWorldLeaveEvent(Long avatarId, Long worldId) {
        String message = String.format(
                "{\"event\":\"WORLD_LEAVE\",\"avatarId\":%d,\"worldId\":%d}",
                avatarId, worldId);
        kafkaTemplate.send("world-events", message);
        log.info("World leave event sent: {}", message);
    }

    public void sendAvatarMoveEvent(Long avatarId, double x, double y) {
        String message = String.format(
                "{\"event\":\"AVATAR_MOVE\",\"avatarId\":%d,\"x\":%.1f,\"y\":%.1f}",
                avatarId, x, y);
        kafkaTemplate.send("avatar-events", message);
        log.info("Avatar move event sent: {}", message);
    }

    public void sendChatEvent(String message) {
        kafkaTemplate.send("chat-events", message);
        log.info("Chat event sent: {}", message);
    }
}