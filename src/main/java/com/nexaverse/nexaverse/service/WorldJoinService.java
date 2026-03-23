package com.nexaverse.nexaverse.service;

import com.nexaverse.nexaverse.entity.Avatar;
import com.nexaverse.nexaverse.entity.WorldRoomEntity;
import com.nexaverse.nexaverse.exception.BadRequestException;
import com.nexaverse.nexaverse.exception.ResourceNotFoundException;
import com.nexaverse.nexaverse.repository.AvatarRepository;
import com.nexaverse.nexaverse.repository.WorldRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WorldJoinService {
    private final WorldEventProducer worldEventProducer;
    private final AvatarRepository avatarRepository;
    private final WorldRepository worldRepository;

    @Transactional
    public Avatar joinWorld(Long avatarId, Long worldId) {
        Avatar avatar = avatarRepository.findById(avatarId)
                .orElseThrow(() -> new ResourceNotFoundException("Avatar not found: " + avatarId));

        WorldRoomEntity world = worldRepository.findById(worldId)
                .orElseThrow(() -> new ResourceNotFoundException("World not found: " + worldId));

        if (!world.isActive()) {
            throw new BadRequestException("World is not active!");
        }

        if (world.getCurrentPlayers() >= world.getMaxPlayers()) {
            throw new BadRequestException("World is full! Max: " + world.getMaxPlayers());
        }

        if (avatar.getCurrentWorld() != null) {
            WorldRoomEntity oldWorld = avatar.getCurrentWorld();
            oldWorld.setCurrentPlayers(Math.max(0, oldWorld.getCurrentPlayers() - 1));
            worldRepository.save(oldWorld);
        }

        avatar.setCurrentWorld(world);
        world.setCurrentPlayers(world.getCurrentPlayers() + 1);
        worldRepository.save(world);
        worldEventProducer.sendWorldJoinEvent(avatarId, worldId);
        return avatarRepository.save(avatar);
    }

    @Transactional
    public Avatar leaveWorld(Long avatarId) {
        Avatar avatar = avatarRepository.findById(avatarId)
                .orElseThrow(() -> new ResourceNotFoundException("Avatar not found: " + avatarId));

        if (avatar.getCurrentWorld() == null) {
            throw new BadRequestException("Avatar is not in any world!");
        }

        WorldRoomEntity world = avatar.getCurrentWorld();
        Long worldId = world.getId(); // ← pehle ID save karo
        world.setCurrentPlayers(Math.max(0, world.getCurrentPlayers() - 1));
        worldRepository.save(world);

        avatar.setCurrentWorld(null);
        avatar.setPositionX(0.0);
        avatar.setPositionY(0.0);
        worldEventProducer.sendWorldLeaveEvent(avatarId, worldId); // ← saved ID use karo
        return avatarRepository.save(avatar);
    }

    public List<Avatar> getPlayersInWorld(Long worldId) {
        worldRepository.findById(worldId)
                .orElseThrow(() -> new ResourceNotFoundException("World not found: " + worldId));
        return avatarRepository.findByCurrentWorld_Id(worldId);
    }
}