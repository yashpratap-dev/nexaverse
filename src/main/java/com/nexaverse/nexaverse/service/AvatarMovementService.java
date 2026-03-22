package com.nexaverse.nexaverse.service;

import com.nexaverse.nexaverse.dto.WebSocketMessage;
import com.nexaverse.nexaverse.entity.HumanAvatar;
import com.nexaverse.nexaverse.exception.ResourceNotFoundException;
import com.nexaverse.nexaverse.repository.AvatarRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class AvatarMovementService {

    private final AvatarRepository avatarRepository;
    private final WorldEventProducer worldEventProducer;
    private final SimpMessagingTemplate messagingTemplate;

    @Transactional
    public void moveAvatar(Long avatarId, double x, double y) {

        HumanAvatar avatar = (HumanAvatar) avatarRepository.findById(avatarId)
                .orElseThrow(() -> new ResourceNotFoundException("Avatar not found: " + avatarId));

        // DB update
        avatar.setPositionX(x);
        avatar.setPositionY(y);
        avatarRepository.save(avatar);

        // Kafka event send karo
        worldEventProducer.sendAvatarMoveEvent(avatarId, x, y);

        // WebSocket se real-time broadcast karo
        if (avatar.getCurrentWorld() != null) {
            WebSocketMessage message = new WebSocketMessage();
            message.setType("AVATAR_MOVED");
            message.setAvatarId(avatarId);
            message.setAvatarName(avatar.getName());
            message.setWorldId(avatar.getCurrentWorld().getId());
            message.setPositionX(x);
            message.setPositionY(y);
            message.setTimestamp(LocalDateTime.now().toString());

            messagingTemplate.convertAndSend(
                    "/topic/world/" + avatar.getCurrentWorld().getId(), message);

            log.info("Avatar {} moved to ({}, {}) — broadcast sent", avatar.getName(), x, y);
        }
    }
}