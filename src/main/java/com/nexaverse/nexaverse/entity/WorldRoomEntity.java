package com.nexaverse.nexaverse.entity;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.persistence.*;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "world_rooms", indexes = {
        @Index(name = "idx_world_active", columnList = "active"),
        @Index(name = "idx_world_type", columnList = "worldType")
})
@Data
public class WorldRoomEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String worldType;

    private int maxPlayers = 50;
    private int currentPlayers = 0;
    private boolean active = true;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}