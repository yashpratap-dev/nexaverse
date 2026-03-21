package com.nexaverse.nexaverse.repository;

import com.nexaverse.nexaverse.entity.Avatar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AvatarRepository extends JpaRepository<Avatar, Long> {
    List<Avatar> findByOwnerId(Long userId);
    List<Avatar> findByCurrentWorldId(Long worldId);
    boolean existsByNameAndOwnerId(String name, Long userId);
}