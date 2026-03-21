package com.nexaverse.nexaverse.controller;

import com.nexaverse.nexaverse.dto.ApiResponse;
import com.nexaverse.nexaverse.dto.WorldDTO;
import com.nexaverse.nexaverse.entity.WorldRoomEntity;
import com.nexaverse.nexaverse.service.WorldService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "Worlds", description = "Virtual world management APIs")
@RestController
@RequestMapping("/api/worlds")
@RequiredArgsConstructor
public class WorldController {

    private final WorldService worldService;

    @PostMapping
    public ResponseEntity<ApiResponse<WorldRoomEntity>> createWorld(
            @Valid @RequestBody WorldDTO dto) {
        WorldRoomEntity world = worldService.createWorld(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("World created!", world));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<WorldRoomEntity>>> getAllWorlds() {
        return ResponseEntity.ok(
                ApiResponse.success("Worlds fetched!", worldService.getAllWorlds()));
    }

    @GetMapping("/active")
    public ResponseEntity<ApiResponse<List<WorldRoomEntity>>> getActiveWorlds() {
        return ResponseEntity.ok(
                ApiResponse.success("Active worlds!", worldService.getActiveWorlds()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<WorldRoomEntity>> getWorldById(@PathVariable Long id) {
        return ResponseEntity.ok(
                ApiResponse.success("World found!", worldService.getWorldById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<WorldRoomEntity>> updateWorld(
            @PathVariable Long id,
            @Valid @RequestBody WorldDTO dto) {
        return ResponseEntity.ok(
                ApiResponse.success("World updated!", worldService.updateWorld(id, dto)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteWorld(@PathVariable Long id) {
        worldService.deleteWorld(id);
        return ResponseEntity.ok(ApiResponse.success("World deleted!", null));
    }
}