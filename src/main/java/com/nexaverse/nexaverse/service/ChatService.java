package com.nexaverse.nexaverse.service;

import com.nexaverse.nexaverse.dto.WebSocketMessage;
import com.nexaverse.nexaverse.entity.ChatMessage;
import com.nexaverse.nexaverse.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatService {

    private final ChatRepository chatRepository;
    private final SimpMessagingTemplate messagingTemplate;
    private final WorldEventProducer worldEventProducer;

    public ChatMessage sendMessage(Long senderId, String senderName,
                                   Long worldId, String content) {
        // DB mein save karo
        ChatMessage message = new ChatMessage();
        message.setContent(content);
        message.setSenderId(senderId);
        message.setSenderName(senderName);
        message.setWorldId(worldId);
        message.setSentAt(LocalDateTime.now());
        ChatMessage saved = chatRepository.save(message);

        // Kafka event
        String kafkaMsg = String.format(
                "{\"event\":\"CHAT\",\"senderId\":%d,\"worldId\":%d,\"content\":\"%s\"}",
                senderId, worldId, content);
        worldEventProducer.sendChatEvent(kafkaMsg);

        // WebSocket broadcast
        WebSocketMessage wsMessage = new WebSocketMessage();
        wsMessage.setType("CHAT_MESSAGE");
        wsMessage.setAvatarId(senderId);
        wsMessage.setAvatarName(senderName);
        wsMessage.setWorldId(worldId);
        wsMessage.setContent(content);
        wsMessage.setTimestamp(LocalDateTime.now().toString());

        messagingTemplate.convertAndSend(
                "/topic/world/" + worldId + "/chat", wsMessage);

        log.info("Chat from {} in world {}: {}", senderName, worldId, content);
        return saved;
    }

    public Page<ChatMessage> getChatHistory(Long worldId, int page, int size) {
        return chatRepository.findByWorldIdOrderBySentAtDesc(
                worldId, PageRequest.of(page, size));
    }
}