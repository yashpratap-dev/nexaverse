package com.nexaverse.nexaverse.repository;

import com.nexaverse.nexaverse.entity.AiCompanion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AiCompanionRepository extends JpaRepository<AiCompanion, Long> {
    Optional<AiCompanion> findByUserId(Long userId);
}