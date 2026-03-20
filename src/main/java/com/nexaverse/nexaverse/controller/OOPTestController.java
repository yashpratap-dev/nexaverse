package com.nexaverse.nexaverse.controller;

import com.nexaverse.nexaverse.entity.BotAvatar;
import com.nexaverse.nexaverse.entity.HumanAvatar;
import com.nexaverse.nexaverse.model.BFSPathfinding;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/oop")
public class OOPTestController {

    private final BFSPathfinding bfs = new BFSPathfinding();

    @GetMapping("/avatars")
    public Map<String, Object> testAvatars() {

        // HumanAvatar
        HumanAvatar human = new HumanAvatar();
        human.setName("Yash");
        human.setLevel(5);
        human.setWeaponType("SWORD");
        human.moveToPosition(10.0, 20.0);

        // BotAvatar
        BotAvatar bot = new BotAvatar();
        bot.setName("NexaBot");
        bot.setLevel(3);
        bot.setAiModel("GPT-NexaVerse");
        bot.setIntelligenceLevel(8);
        bot.moveToPosition(5.0, 15.0);

        return Map.of(
                "human_status",   human.getStatus(),
                "human_ability",  human.getSpecialAbility(),
                "human_attack",   human.getAttackPower(),
                "bot_status",     bot.getStatus(),
                "bot_ability",    bot.getSpecialAbility(),
                "bot_attack",     bot.getAttackPower()
        );
    }

    @GetMapping("/pathfinding")
    public Map<String, Object> testPathfinding() {

        // 5x5 grid — 0 = walkable, 1 = wall
        int[][] grid = {
                {0, 0, 0, 0, 0},
                {0, 1, 1, 1, 0},
                {0, 0, 0, 1, 0},
                {0, 1, 0, 0, 0},
                {0, 0, 0, 0, 0}
        };

        int[] start = {0, 0};
        int[] end   = {4, 4};

        List<int[]> path = bfs.findPath(grid, start, end);

        List<String> pathStr = path.stream()
                .map(p -> "(" + p[0] + "," + p[1] + ")")
                .toList();

        return Map.of(
                "strategy",    bfs.getStrategyName(),
                "start",       "(" + start[0] + "," + start[1] + ")",
                "end",         "(" + end[0] + "," + end[1] + ")",
                "path_length", path.size(),
                "path",        pathStr
        );
    }
}