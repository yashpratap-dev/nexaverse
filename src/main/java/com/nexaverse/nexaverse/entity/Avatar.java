package com.nexaverse.nexaverse.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "avatars", indexes = {
        @Index(name = "idx_avatar_user", columnList = "user_id"),
        @Index(name = "idx_avatar_world", columnList = "world_id")
})
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Avatar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String avatarType;

    private int level = 1;
    private double positionX = 0.0;
    private double positionY = 0.0;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "world_id")
    @JsonIgnore
    private WorldRoomEntity currentWorld;

    public abstract String getSpecialAbility();
    public abstract int getAttackPower();

    public void moveToPosition(double x, double y) {
        this.positionX = x;
        this.positionY = y;
    }

    public String getStatus() {
        return String.format("%s (Level %d) at (%.1f, %.1f)", name, level, positionX, positionY);
    }
}