package com.nexaverse.nexaverse.controller;

import com.nexaverse.nexaverse.dto.ApiResponse;
import com.nexaverse.nexaverse.dto.AvatarDTO;
import com.nexaverse.nexaverse.service.AvatarMovementService;
import com.nexaverse.nexaverse.service.AvatarService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@Tag(name = "Avatars", description = "Avatar management APIs")
@RestController
@RequestMapping("/api/avatars")
@RequiredArgsConstructor
public class AvatarController {

    private final AvatarService avatarService;
    private final AvatarMovementService avatarMovementService;

    @PostMapping
    public ResponseEntity<ApiResponse<?>> createAvatar(@Valid @RequestBody AvatarDTO dto) {
        var avatar = avatarService.createAvatar(dto);
        Map<String, Object> result = Map.of(
                "avatarId", avatar.getId(),
                "avatarName", avatar.getName(),
                "avatarType", avatar.getAvatarType(),
                "level", avatar.getLevel()
        );
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Avatar created!", result));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<?>>> getAllAvatars() {
        return ResponseEntity.ok(
                ApiResponse.success("Avatars fetched!", avatarService.getAllAvatars()));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<?>>> getAvatarsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(
                ApiResponse.success("User avatars!", avatarService.getAvatarsByUser(userId)));
    }

    @PatchMapping("/{id}/move")
    public ResponseEntity<ApiResponse<Map<String, Object>>> moveAvatar(
            @PathVariable Long id,
            @RequestParam double x,
            @RequestParam double y) {
        avatarMovementService.moveAvatar(id, x, y);
        Map<String, Object> result = Map.of(
                "avatarId", id,
                "positionX", x,
                "positionY", y,
                "status", "MOVED",
                "realtime", "broadcast sent"
        );
        return ResponseEntity.ok(ApiResponse.success("Avatar moved!", result));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteAvatar(@PathVariable Long id) {
        avatarService.deleteAvatar(id);
        return ResponseEntity.ok(ApiResponse.success("Avatar deleted!", null));
    }
}