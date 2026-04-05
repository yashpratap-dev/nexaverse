package com.nexaverse.nexaverse.controller;

import com.nexaverse.nexaverse.dto.ApiResponse;
import com.nexaverse.nexaverse.service.LeaderboardService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/leaderboard")
@RequiredArgsConstructor
@Tag(name = "Leaderboard", description = "Redis-powered leaderboard & achievements")
public class LeaderboardController {

    private final LeaderboardService leaderboardService;

    @GetMapping("/top")
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> getTopPlayers(
            @RequestParam(defaultValue = "10") int limit) {
        List<Map<String, Object>> players = leaderboardService.getTopPlayers(limit);
        return ResponseEntity.ok(ApiResponse.success("Top players!", players));
    }

    @GetMapping("/rank/{username}")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getPlayerRank(
            @PathVariable String username) {
        Map<String, Object> rank = leaderboardService.getPlayerRank(username);
        return ResponseEntity.ok(ApiResponse.success("Player rank!", rank));
    }

    @PostMapping("/score")
    public ResponseEntity<ApiResponse<String>> addScore(
            @RequestBody Map<String, Object> request) {
        String username = (String) request.get("username");
        double score = Double.parseDouble(request.get("score").toString());
        leaderboardService.addScore(username, score);
        return ResponseEntity.ok(ApiResponse.success("Score updated!", "OK"));
    }

    @PostMapping("/score/increment")
    public ResponseEntity<ApiResponse<String>> incrementScore(
            @RequestBody Map<String, Object> request) {
        String username = (String) request.get("username");
        double delta = Double.parseDouble(request.get("delta").toString());
        leaderboardService.incrementScore(username, delta);
        return ResponseEntity.ok(ApiResponse.success("Score incremented!", "OK"));
    }

    @PostMapping("/achievement")
    public ResponseEntity<ApiResponse<String>> unlockAchievement(
            @RequestBody Map<String, Object> request) {
        String username = (String) request.get("username");
        String achievement = (String) request.get("achievement");
        leaderboardService.unlockAchievement(username, achievement);
        return ResponseEntity.ok(ApiResponse.success("Achievement unlocked!", achievement));
    }

    @GetMapping("/achievements/{username}")
    public ResponseEntity<ApiResponse<Set<Object>>> getAchievements(
            @PathVariable String username) {
        Set<Object> achievements = leaderboardService.getAchievements(username);
        return ResponseEntity.ok(ApiResponse.success("Achievements!", achievements));
    }

    @PostMapping("/seed")
    public ResponseEntity<ApiResponse<String>> seedData() {
        leaderboardService.seedDemoData();
        return ResponseEntity.ok(ApiResponse.success("Demo data seeded!", "OK"));
    }
}