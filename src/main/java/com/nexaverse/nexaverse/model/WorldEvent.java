package com.nexaverse.nexaverse.model;

import java.time.Instant;

// Sealed class — sirf ye events allowed hain
public sealed interface WorldEvent
        permits WorldEvent.AvatarMoved,
        WorldEvent.ChatSent,
        WorldEvent.WorldJoined,
        WorldEvent.WorldLeft {

    Instant timestamp();

    record AvatarMoved(Long avatarId, double x, double y, Instant timestamp)
            implements WorldEvent {}

    record ChatSent(Long avatarId, String message, Instant timestamp)
            implements WorldEvent {}

    record WorldJoined(Long avatarId, String worldName, Instant timestamp)
            implements WorldEvent {}

    record WorldLeft(Long avatarId, String worldName, Instant timestamp)
            implements WorldEvent {}
}