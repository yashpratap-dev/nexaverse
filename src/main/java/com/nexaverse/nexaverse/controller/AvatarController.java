package com.nexaverse.nexaverse.controller;

import com.nexaverse.nexaverse.dto.ApiResponse;
import com.nexaverse.nexaverse.dto.AvatarDTO;
import com.nexaverse.nexaverse.service.AvatarService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/avatars")
@RequiredArgsConstructor
public class AvatarController {

    private final AvatarService avatarService;

    @PostMapping
    public ResponseEntity<ApiResponse<?>> createAvatar(@Valid @RequestBody AvatarDTO dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Avatar created!", avatarService.createAvatar(dto)));
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
    public ResponseEntity<ApiResponse<?>> moveAvatar(
            @PathVariable Long id,
            @RequestParam double x,
            @RequestParam double y) {
        return ResponseEntity.ok(
                ApiResponse.success("Avatar moved!", avatarService.moveAvatar(id, x, y)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteAvatar(@PathVariable Long id) {
        avatarService.deleteAvatar(id);
        return ResponseEntity.ok(ApiResponse.success("Avatar deleted!", null));
    }
}
