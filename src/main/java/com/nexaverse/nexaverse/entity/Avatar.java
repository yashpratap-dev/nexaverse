package com.nexaverse.nexaverse.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "avatars")
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

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;

    // Abstract methods — subclass implement karenge
    public abstract String getSpecialAbility();
    public abstract int getAttackPower();

    // Common method — sab avatars ke liye
    public void moveToPosition(double x, double y) {
        this.positionX = x;
        this.positionY = y;
    }

    public String getStatus() {
        return String.format("%s (Level %d) at (%.1f, %.1f)", name, level, positionX, positionY);
    }
}