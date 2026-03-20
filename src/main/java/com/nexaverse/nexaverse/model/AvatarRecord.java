package com.nexaverse.nexaverse.model;

public record AvatarRecord(
        Long id,
        String name,
        String type,
        int level,
        double positionX,
        double positionY
) {
    // Custom validation — Java 21 compact constructor
    public AvatarRecord {
        if (level < 1) throw new IllegalArgumentException("Level 1 se kam nahi ho sakta!");
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Name empty nahi ho sakta!");
    }
}