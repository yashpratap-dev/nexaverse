package com.nexaverse.nexaverse.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.io.Serializable;

@Entity
@Table(name = "ai_companions")
@Data
public class AiCompanion implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, length = 1000)
    private String personality;

    @Column(nullable = false)
    private String companionType;

    @Column(nullable = false)
    private Long userId;

    private String customName;
}
