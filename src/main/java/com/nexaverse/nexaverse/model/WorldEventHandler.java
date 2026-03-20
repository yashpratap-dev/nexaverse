package com.nexaverse.nexaverse.model;

public class WorldEventHandler {

    // Pattern matching with switch — Java 21 feature
    public String handleEvent(WorldEvent event) {
        return switch (event) {
            case WorldEvent.AvatarMoved m ->
                    String.format("Avatar %d moved to (%.1f, %.1f)", m.avatarId(), m.x(), m.y());

            case WorldEvent.ChatSent c ->
                    String.format("Avatar %d said: %s", c.avatarId(), c.message());

            case WorldEvent.WorldJoined j ->
                    String.format("Avatar %d joined world: %s", j.avatarId(), j.worldName());

            case WorldEvent.WorldLeft l ->
                    String.format("Avatar %d left world: %s", l.avatarId(), l.worldName());
        };
    }

    // Pattern matching with instanceof — Java 21
    public String describeWorld(WorldRoom room) {
        return switch (room) {
            case WorldRoom.ForestWorld f ->
                    String.format("Forest: %s, Biome: %s, Max: %d players", f.name(), f.biome(), f.maxPlayers());

            case WorldRoom.CityWorld c ->
                    String.format("City: %s, Type: %s, Max: %d players", c.name(), c.cityType(), c.maxPlayers());

            case WorldRoom.SpaceWorld s ->
                    String.format("Space: %s, Planet: %s, Max: %d players", s.name(), s.planet(), s.maxPlayers());
        };
    }
}