package com.nexaverse.nexaverse.model;

// Sealed class — sirf ye 3 types allowed hain
public sealed interface WorldRoom
        permits WorldRoom.ForestWorld, WorldRoom.CityWorld, WorldRoom.SpaceWorld {

    String getName();
    int getMaxPlayers();

    // Forest World
    record ForestWorld(String name, int maxPlayers, String biome)
            implements WorldRoom {
        public String getName() { return name; }
        public int getMaxPlayers() { return maxPlayers; }
    }

    // City World
    record CityWorld(String name, int maxPlayers, String cityType)
            implements WorldRoom {
        public String getName() { return name; }
        public int getMaxPlayers() { return maxPlayers; }
    }

    // Space World
    record SpaceWorld(String name, int maxPlayers, String planet)
            implements WorldRoom {
        public String getName() { return name; }
        public int getMaxPlayers() { return maxPlayers; }
    }
}