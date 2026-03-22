package com.nexaverse.nexaverse.repository;

import com.nexaverse.nexaverse.entity.Quest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface QuestRepository extends JpaRepository<Quest, Long> {
    List<Quest> findByUserId(Long userId);
    List<Quest> findByUserIdAndCompleted(Long userId, boolean completed);
}