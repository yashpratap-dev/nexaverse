package com.nexaverse.nexaverse.controller;

import com.nexaverse.nexaverse.dto.ApiResponse;
import com.nexaverse.nexaverse.entity.AiCompanion;
import com.nexaverse.nexaverse.service.AiCompanionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
@Tag(name = "AI Companion", description = "MIMIR & GUANYIN AI companion APIs")
public class AiCompanionController {

    private final AiCompanionService aiCompanionService;

    @PostMapping("/chat/{userId}")
    public ResponseEntity<ApiResponse<Map<String, String>>> chat(
            @PathVariable Long userId,
            @RequestBody Map<String, String> request) {
        String message = request.get("message");
        String response = aiCompanionService.chat(userId, message);
        return ResponseEntity.ok(ApiResponse.success("AI response!",
                Map.of("response", response)));
    }

    @PostMapping("/select/{userId}")
    public ResponseEntity<ApiResponse<AiCompanion>> selectCompanion(
            @PathVariable Long userId,
            @RequestBody Map<String, String> request) {
        String companionType = request.get("companionType");
        String customName = request.get("customName");
        AiCompanion companion = aiCompanionService.selectCompanion(
                userId, companionType, customName);
        return ResponseEntity.ok(ApiResponse.success("Companion selected!", companion));
    }
}