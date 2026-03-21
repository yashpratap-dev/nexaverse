package com.nexaverse.nexaverse.service;

import com.nexaverse.nexaverse.dto.WorldDTO;
import com.nexaverse.nexaverse.entity.WorldRoomEntity;
import com.nexaverse.nexaverse.exception.DuplicateResourceException;
import com.nexaverse.nexaverse.exception.ResourceNotFoundException;
import com.nexaverse.nexaverse.repository.WorldRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WorldService {

    private final WorldRepository worldRepository;

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

    public List<WorldRoomEntity> getAllWorlds() {
        return worldRepository.findAll();
    }

    public List<WorldRoomEntity> getActiveWorlds() {
        return worldRepository.findByActiveTrue();
    }

    public WorldRoomEntity getWorldById(Long id) {
        return worldRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("World not found: " + id));
    }

    public WorldRoomEntity updateWorld(Long id, WorldDTO dto) {
        WorldRoomEntity world = getWorldById(id);
        world.setName(dto.getName());
        world.setWorldType(dto.getWorldType());
        world.setMaxPlayers(dto.getMaxPlayers());
        world.setActive(dto.isActive());
        return worldRepository.save(world);
    }

    public void deleteWorld(Long id) {
        WorldRoomEntity world = getWorldById(id);
        worldRepository.delete(world);
    }
}
