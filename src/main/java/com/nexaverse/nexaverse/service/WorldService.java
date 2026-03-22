package com.nexaverse.nexaverse.service;

import com.nexaverse.nexaverse.dto.WorldDTO;
import com.nexaverse.nexaverse.entity.WorldRoomEntity;
import com.nexaverse.nexaverse.exception.DuplicateResourceException;
import com.nexaverse.nexaverse.exception.ResourceNotFoundException;
import com.nexaverse.nexaverse.repository.WorldRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WorldService {

    private final WorldRepository worldRepository;

    @CacheEvict(value = "worlds", allEntries = true)
    public WorldRoomEntity createWorld(WorldDTO dto) {
        if (worldRepository.existsByName(dto.getName())) {
            throw new DuplicateResourceException("World already exists: " + dto.getName());
        }
        WorldRoomEntity world = new WorldRoomEntity();
        world.setName(dto.getName());
        world.setWorldType(dto.getWorldType());
        world.setMaxPlayers(dto.getMaxPlayers());
        world.setActive(dto.isActive());
        return worldRepository.save(world);
    }

    @Cacheable(value = "worlds")
    public List<WorldRoomEntity> getAllWorlds() {
        return worldRepository.findAll();
    }

    @Cacheable(value = "activeWorlds")
    public List<WorldRoomEntity> getActiveWorlds() {
        return worldRepository.findByActiveTrue();
    }

    @Cacheable(value = "world", key = "#id")
    public WorldRoomEntity getWorldById(Long id) {
        return worldRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("World not found: " + id));
    }

    @CachePut(value = "world", key = "#id")
    @CacheEvict(value = {"worlds", "activeWorlds"}, allEntries = true)
    public WorldRoomEntity updateWorld(Long id, WorldDTO dto) {
        WorldRoomEntity world = worldRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("World not found: " + id));
        world.setName(dto.getName());
        world.setWorldType(dto.getWorldType());
        world.setMaxPlayers(dto.getMaxPlayers());
        world.setActive(dto.isActive());
        return worldRepository.save(world);
    }

    @CacheEvict(value = {"worlds", "world", "activeWorlds"}, allEntries = true)
    public void deleteWorld(Long id) {
        WorldRoomEntity world = worldRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("World not found: " + id));
        worldRepository.delete(world);
    }
}