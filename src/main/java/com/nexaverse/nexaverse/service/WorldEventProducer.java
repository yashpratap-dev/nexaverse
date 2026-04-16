package com.nexaverse.nexaverse.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@ConditionalOnBean(KafkaTemplate.class)
public class WorldEventProducer {

    @Autowired(required = false)
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendWorldJoinEvent(Long avatarId, Long worldId) {
        if (kafkaTemplate == null) return;
        String message = String.format(
                "{\"event\":\"WORLD_JOIN\",\"avatarId\":%d,\"worldId\":%d}",
                avatarId, worldId);
        kafkaTemplate.send("world-events", message);
        log.info("World join event sent: {}", message);
    }

    public void sendWorldLeaveEvent(Long avatarId, Long worldId) {
        if (kafkaTemplate == null) return;
        String message = String.format(
                "{\"event\":\"WORLD_LEAVE\",\"avatarId\":%d,\"worldId\":%d}",
                avatarId, worldId);
        kafkaTemplate.send("world-events", message);
        log.info("World leave event sent: {}", message);
    }

    public void sendAvatarMoveEvent(Long avatarId, double x, double y) {
        if (kafkaTemplate == null) return;
        String message = String.format(
                "{\"event\":\"AVATAR_MOVE\",\"avatarId\":%d,\"x\":%.1f,\"y\":%.1f}",
                avatarId, x, y);
        kafkaTemplate.send("avatar-events", message);
        log.info("Avatar move event sent: {}", message);
    }

    public void sendChatEvent(String message) {
        if (kafkaTemplate == null) return;
        kafkaTemplate.send("chat-events", message);
        log.info("Chat event sent: {}", message);
    }
}