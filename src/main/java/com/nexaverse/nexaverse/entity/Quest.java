package com.nexaverse.nexaverse.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "quests")
@Data
public class Quest implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 1000)
    private String description;

    @Column(nullable = false)
    private String difficulty; // EASY, MEDIUM, HARD, LEGENDARY

    @Column(nullable = false)
    private String questType; // COMBAT, EXPLORE, CRAFT, SOCIAL

    private int rewardXP;
    private String rewardItem;

    @Column(nullable = false)
    private Long userId;

    private boolean completed = false;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}