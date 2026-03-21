package com.nexaverse.nexaverse.repository;

import com.nexaverse.nexaverse.entity.WorldRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface WorldRepository extends JpaRepository<WorldRoomEntity, Long> {
    Optional<WorldRoomEntity> findByName(String name);
    List<WorldRoomEntity> findByWorldType(String worldType);
    List<WorldRoomEntity> findByActiveTrue();
    boolean existsByName(String name);
}
