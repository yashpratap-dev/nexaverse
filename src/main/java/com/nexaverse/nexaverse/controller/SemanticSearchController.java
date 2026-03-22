package com.nexaverse.nexaverse.controller;

import com.nexaverse.nexaverse.dto.ApiResponse;
import com.nexaverse.nexaverse.entity.WorldRoomEntity;
import com.nexaverse.nexaverse.service.SemanticSearchService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
@Tag(name = "Semantic Search", description = "AI Powered World Search APIs")
public class SemanticSearchController {

    private final SemanticSearchService semanticSearchService;

    @GetMapping("/worlds")
    public ResponseEntity<ApiResponse<List<WorldRoomEntity>>> searchWorlds(
            @RequestParam String query) {
        List<WorldRoomEntity> worlds = semanticSearchService.semanticSearch(query);
        return ResponseEntity.ok(ApiResponse.success("Search results!", worlds));
    }

    @GetMapping("/recommend/{userId}")
    public ResponseEntity<ApiResponse<Map<String, Object>>> recommendWorld(
            @PathVariable Long userId,
            @RequestParam String mood) {
        Map<String, Object> recommendation =
                semanticSearchService.getWorldRecommendation(mood, userId);
        return ResponseEntity.ok(ApiResponse.success("Recommendation!", recommendation));
    }
}