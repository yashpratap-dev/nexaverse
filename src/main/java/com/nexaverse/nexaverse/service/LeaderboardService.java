package com.nexaverse.nexaverse.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class LeaderboardService {

    private final RedisTemplate<String, Object> redisTemplate;

    private static final String LEADERBOARD_KEY = "nexaverse:leaderboard:global";
    private static final String ACHIEVEMENTS_KEY = "nexaverse:achievements:";

    // Score add karo / update karo
    public void addScore(String username, double score) {
        redisTemplate.opsForZSet().add(LEADERBOARD_KEY, username, score);
        log.info("Score updated: {} -> {}", username, score);
    }

    // Score increment karo
    public void incrementScore(String username, double delta) {
        redisTemplate.opsForZSet().incrementScore(LEADERBOARD_KEY, username, delta);
        log.info("Score incremented: {} by {}", username, delta);
    }

    // Top N players fetch karo
    public List<Map<String, Object>> getTopPlayers(int n) {
        Set<ZSetOperations.TypedTuple<Object>> topPlayers =
                redisTemplate.opsForZSet().reverseRangeWithScores(LEADERBOARD_KEY, 0, n - 1);

        List<Map<String, Object>> result = new ArrayList<>();
        if (topPlayers == null) return result;

        int rank = 1;
        for (ZSetOperations.TypedTuple<Object> entry : topPlayers) {
            Map<String, Object> player = new HashMap<>();
            player.put("rank", rank++);
            player.put("username", entry.getValue());
            player.put("score", entry.getScore());
            result.add(player);
        }
        return result;
    }

    // Player ki rank fetch karo
    public Map<String, Object> getPlayerRank(String username) {
        Long rank = redisTemplate.opsForZSet().reverseRank(LEADERBOARD_KEY, username);
        Double score = redisTemplate.opsForZSet().score(LEADERBOARD_KEY, username);

        Map<String, Object> result = new HashMap<>();
        result.put("username", username);
        result.put("rank", rank != null ? rank + 1 : -1);
        result.put("score", score != null ? score : 0);
        return result;
    }

    // Achievement unlock karo
    public void unlockAchievement(String username, String achievement) {
        String key = ACHIEVEMENTS_KEY + username;
        redisTemplate.opsForSet().add(key, achievement);
        log.info("Achievement unlocked: {} -> {}", username, achievement);
    }

    // Player ke achievements fetch karo
    public Set<Object> getAchievements(String username) {
        String key = ACHIEVEMENTS_KEY + username;
        Set<Object> achievements = redisTemplate.opsForSet().members(key);
        return achievements != null ? achievements : new HashSet<>();
    }

    // Seed karo demo data
    public void seedDemoData() {
        String[][] demoPlayers = {
                {"DragonSlayer", "1500"},
                {"ShadowMage", "1200"},
                {"IronWarrior", "1100"},
                {"PhoenixRanger", "950"},
                {"VoidRogue", "800"},
        };
        for (String[] player : demoPlayers) {
            redisTemplate.opsForZSet().add(
                    LEADERBOARD_KEY, player[0], Double.parseDouble(player[1])
            );
        }
        log.info("Demo leaderboard data seeded!");
    }
}