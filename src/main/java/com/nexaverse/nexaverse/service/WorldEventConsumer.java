package com.nexaverse.nexaverse.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class WorldEventConsumer {

    @KafkaListener(topics = "world-events", groupId = "nexaverse-group")
    public void consumeWorldEvent(String message) {
        log.info("World event received: {}", message);
    }

    @KafkaListener(topics = "avatar-events", groupId = "nexaverse-group")
    public void consumeAvatarEvent(String message) {
        log.info("Avatar event received: {}", message);
    }
}