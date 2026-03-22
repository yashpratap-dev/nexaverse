package com.nexaverse.nexaverse.controller;

import com.nexaverse.nexaverse.dto.ApiResponse;
import com.nexaverse.nexaverse.entity.Quest;
import com.nexaverse.nexaverse.service.QuestService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/quests")
@RequiredArgsConstructor
@Tag(name = "Quests", description = "AI Quest Generator APIs")
public class QuestController {

    private final QuestService questService;

    @PostMapping("/generate/{userId}")
    public ResponseEntity<ApiResponse<Quest>> generateQuest(
            @PathVariable Long userId,
            @RequestBody Map<String, String> request) {
        String avatarType = request.getOrDefault("avatarType", "WARRIOR");
        String worldType = request.getOrDefault("worldType", "FOREST");
        Quest quest = questService.generateQuest(userId, avatarType, worldType);
        return ResponseEntity.ok(ApiResponse.success("Quest generated!", quest));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<Quest>>> getUserQuests(@PathVariable Long userId) {
        return ResponseEntity.ok(
                ApiResponse.success("Quests fetched!", questService.getUserQuests(userId)));
    }

    @PatchMapping("/{questId}/complete")
    public ResponseEntity<ApiResponse<Quest>> completeQuest(@PathVariable Long questId) {
        return ResponseEntity.ok(
                ApiResponse.success("Quest completed!", questService.completeQuest(questId)));
    }
}