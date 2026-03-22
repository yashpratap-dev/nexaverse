package com.nexaverse.nexaverse.service;

import com.nexaverse.nexaverse.dto.WorldDTO;
import com.nexaverse.nexaverse.entity.WorldRoomEntity;
import com.nexaverse.nexaverse.exception.DuplicateResourceException;
import com.nexaverse.nexaverse.exception.ResourceNotFoundException;
import com.nexaverse.nexaverse.repository.WorldRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WorldServiceTest {

    @Mock
    private WorldRepository worldRepository;

    @InjectMocks
    private WorldService worldService;

    private WorldDTO worldDTO;
    private WorldRoomEntity world;

    @BeforeEach
    void setUp() {
        worldDTO = new WorldDTO();
        worldDTO.setName("Dark Forest");
        worldDTO.setWorldType("FOREST");
        worldDTO.setMaxPlayers(50);
        worldDTO.setActive(true);

        world = new WorldRoomEntity();
        world.setId(1L);
        world.setName("Dark Forest");
        world.setWorldType("FOREST");
        world.setMaxPlayers(50);
        world.setActive(true);
    }

    @Test
    void createWorld_Success() {
        when(worldRepository.existsByName("Dark Forest")).thenReturn(false);
        when(worldRepository.save(any(WorldRoomEntity.class))).thenReturn(world);

        WorldRoomEntity result = worldService.createWorld(worldDTO);

        assertNotNull(result);
        assertEquals("Dark Forest", result.getName());
        assertEquals("FOREST", result.getWorldType());
        verify(worldRepository, times(1)).save(any(WorldRoomEntity.class));
    }

    @Test
    void createWorld_DuplicateName_ThrowsException() {
        when(worldRepository.existsByName("Dark Forest")).thenReturn(true);

        assertThrows(DuplicateResourceException.class,
                () -> worldService.createWorld(worldDTO));
        verify(worldRepository, never()).save(any());
    }

    @Test
    void getWorldById_Success() {
        when(worldRepository.findById(1L)).thenReturn(Optional.of(world));

        WorldRoomEntity result = worldService.getWorldById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void getWorldById_NotFound_ThrowsException() {
        when(worldRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> worldService.getWorldById(999L));
    }

    @Test
    void deleteWorld_Success() {
        when(worldRepository.findById(1L)).thenReturn(Optional.of(world));
        doNothing().when(worldRepository).delete(world);

        assertDoesNotThrow(() -> worldService.deleteWorld(1L));
        verify(worldRepository, times(1)).delete(world);
    }
}