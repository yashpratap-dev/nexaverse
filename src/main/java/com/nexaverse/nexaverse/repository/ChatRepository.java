package com.nexaverse.nexaverse.repository;

import com.nexaverse.nexaverse.entity.ChatMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends JpaRepository<ChatMessage, Long> {
    Page<ChatMessage> findByWorldIdOrderBySentAtDesc(Long worldId, Pageable pageable);
}