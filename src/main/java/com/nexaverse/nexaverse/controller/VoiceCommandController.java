package com.nexaverse.nexaverse.controller;

import com.nexaverse.nexaverse.dto.ApiResponse;
import com.nexaverse.nexaverse.service.VoiceCommandService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/voice")
@RequiredArgsConstructor
@Tag(name = "Voice Commands", description = "AI Voice Command Processing APIs")
public class VoiceCommandController {

    private final VoiceCommandService voiceCommandService;

    @PostMapping("/command/{userId}")
    public ResponseEntity<ApiResponse<Map<String, Object>>> processCommand(
            @PathVariable Long userId,
            @RequestBody Map<String, String> request) {
        String voiceText = request.get("command");
        Map<String, Object> result = voiceCommandService.processCommand(voiceText, userId);
        return ResponseEntity.ok(ApiResponse.success("Command processed!", result));
    }
}