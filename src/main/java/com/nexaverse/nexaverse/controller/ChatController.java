package com.nexaverse.nexaverse.controller;

import com.nexaverse.nexaverse.dto.ApiResponse;
import com.nexaverse.nexaverse.entity.ChatMessage;
import com.nexaverse.nexaverse.service.ChatService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
@Tag(name = "Chat", description = "Real-time chat APIs")
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/send")
    public ResponseEntity<ApiResponse<ChatMessage>> sendMessage(
            @RequestBody Map<String, Object> request) {
        Long senderId = Long.valueOf(request.get("senderId").toString());
        String senderName = request.get("senderName").toString();
        Long worldId = Long.valueOf(request.get("worldId").toString());
        String content = request.get("content").toString();

        ChatMessage message = chatService.sendMessage(senderId, senderName, worldId, content);
        return ResponseEntity.ok(ApiResponse.success("Message sent!", message));
    }

    @GetMapping("/history/{worldId}")
    public ResponseEntity<ApiResponse<Page<ChatMessage>>> getChatHistory(
            @PathVariable Long worldId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(
                ApiResponse.success("Chat history!",
                        chatService.getChatHistory(worldId, page, size)));
    }
}