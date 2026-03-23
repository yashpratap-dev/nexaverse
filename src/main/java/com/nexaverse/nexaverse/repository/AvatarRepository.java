package com.nexaverse.nexaverse.repository;

import com.nexaverse.nexaverse.entity.Avatar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AvatarRepository extends JpaRepository<Avatar, Long> {

    // ✅ user.id
    List<Avatar> findByUser_Id(Long userId);

    // ✅ world.id
    List<Avatar> findByCurrentWorld_Id(Long worldId);

    // ✅ name + user.id
    boolean existsByNameAndUser_Id(String name, Long userId);

    // ✅ fetch with join
    @Query("SELECT a FROM Avatar a LEFT JOIN FETCH a.currentWorld WHERE a.id = :id")
    Optional<Avatar> findByIdWithWorld(@Param("id") Long id);

    // ✅ count by world
    @Query("SELECT COUNT(a) FROM Avatar a WHERE a.currentWorld.id = :worldId")
    Long countByWorldId(@Param("worldId") Long worldId);
}