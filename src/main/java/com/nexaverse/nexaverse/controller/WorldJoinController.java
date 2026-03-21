package com.nexaverse.nexaverse.controller;

import com.nexaverse.nexaverse.dto.ApiResponse;
import com.nexaverse.nexaverse.entity.Avatar;
import com.nexaverse.nexaverse.service.WorldJoinService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@Tag(name = "World Join", description = "Avatar join/leave world APIs")
@RestController
@RequestMapping("/api/worlds")
@RequiredArgsConstructor
public class WorldJoinController {

    private final WorldJoinService worldJoinService;

    @PostMapping("/{worldId}/join/{avatarId}")
    public ResponseEntity<ApiResponse<Map<String, Object>>> joinWorld(
            @PathVariable Long worldId,
            @PathVariable Long avatarId) {
        Avatar avatar = worldJoinService.joinWorld(avatarId, worldId);
        Map<String, Object> result = Map.of(
                "avatarId", avatar.getId(),
                "avatarName", avatar.getName(),
                "worldId", worldId,
                "positionX", avatar.getPositionX(),
                "positionY", avatar.getPositionY(),
                "status", "JOINED"
        );
        return ResponseEntity.ok(ApiResponse.success("Joined world!", result));
    }

    @PostMapping("/leave/{avatarId}")
    public ResponseEntity<ApiResponse<Map<String, Object>>> leaveWorld(
            @PathVariable Long avatarId) {
        Avatar avatar = worldJoinService.leaveWorld(avatarId);
        Map<String, Object> result = Map.of(
                "avatarId", avatar.getId(),
                "avatarName", avatar.getName(),
                "status", "LEFT"
        );
        return ResponseEntity.ok(ApiResponse.success("Left world!", result));
    }

    @GetMapping("/{worldId}/players")
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> getPlayersInWorld(
            @PathVariable Long worldId) {
        List<Avatar> players = worldJoinService.getPlayersInWorld(worldId);
        List<Map<String, Object>> result = players.stream()
                .map(a -> Map.<String, Object>of(
                        "avatarId", a.getId(),
                        "avatarName", a.getName(),
                        "level", a.getLevel(),
                        "positionX", a.getPositionX(),
                        "positionY", a.getPositionY()
                ))
                .toList();
        return ResponseEntity.ok(ApiResponse.success("Players in world!", result));
    }
}
