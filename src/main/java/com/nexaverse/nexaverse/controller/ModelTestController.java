package com.nexaverse.nexaverse.controller;

import com.nexaverse.nexaverse.model.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/test")
public class ModelTestController {

    private final WorldEventHandler handler = new WorldEventHandler();

    @GetMapping("/avatar")
    public AvatarRecord testAvatar() {
        return new AvatarRecord(1L, "Yash", "WARRIOR", 5, 10.5, 20.3);
    }

    @GetMapping("/worlds")
    public List<String> testWorlds() {
        var forest = new WorldRoom.ForestWorld("Dark Forest", 50, "TROPICAL");
        var city   = new WorldRoom.CityWorld("Neo Mumbai", 100, "CYBERPUNK");
        var space  = new WorldRoom.SpaceWorld("Andromeda", 30, "MARS");

        return List.of(
                handler.describeWorld(forest),
                handler.describeWorld(city),
                handler.describeWorld(space)
        );
    }

    @GetMapping("/events")
    public List<String> testEvents() {
        var moved  = new WorldEvent.AvatarMoved(1L, 15.0, 25.0, Instant.now());
        var chat   = new WorldEvent.ChatSent(1L, "Hello NexaVerse!", Instant.now());
        var joined = new WorldEvent.WorldJoined(1L, "Dark Forest", Instant.now());

        return List.of(
                handler.handleEvent(moved),
                handler.handleEvent(chat),
                handler.handleEvent(joined)
        );
    }

    @GetMapping("/all")
    public Map<String, Object> testAll() {
        return Map.of(
                "avatar",  testAvatar(),
                "worlds",  testWorlds(),
                "events",  testEvents()
        );
    }
}