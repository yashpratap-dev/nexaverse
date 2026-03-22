package com.nexaverse.nexaverse.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexaverse.nexaverse.entity.Quest;
import com.nexaverse.nexaverse.repository.QuestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuestService {

    private final QuestRepository questRepository;
    private final RestTemplate restTemplate;

    @Value("${groq.api.key}")
    private String groqApiKey;

    private static final String GROQ_URL = "https://api.groq.com/openai/v1/chat/completions";

    public Quest generateQuest(Long userId, String avatarType, String worldType) {
        String prompt = String.format("""
            Generate a quest for a %s avatar in a %s world.
            Respond ONLY with valid JSON, no extra text:
            {
              "title": "quest title",
              "description": "2-3 sentence quest description",
              "difficulty": "EASY or MEDIUM or HARD or LEGENDARY",
              "questType": "COMBAT or EXPLORE or CRAFT or SOCIAL",
              "rewardXP": number between 100-1000,
              "rewardItem": "item name"
            }
            """, avatarType, worldType);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(groqApiKey);

        Map<String, Object> body = new HashMap<>();
        body.put("model", "llama-3.3-70b-versatile");
        body.put("messages", List.of(Map.of("role", "user", "content", prompt)));
        body.put("max_tokens", 300);
        body.put("temperature", 0.9);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(
                    GROQ_URL, new HttpEntity<>(body, headers), Map.class);

            List<Map<String, Object>> choices =
                    (List<Map<String, Object>>) response.getBody().get("choices");
            String content = (String)
                    ((Map<String, Object>) choices.get(0).get("message")).get("content");

            // Clean JSON
            content = content.trim();
            if (content.contains("```")) {
                content = content.replaceAll("```json", "").replaceAll("```", "").trim();
            }

            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> questData = mapper.readValue(content, Map.class);

            Quest quest = new Quest();
            quest.setUserId(userId);
            quest.setTitle((String) questData.get("title"));
            quest.setDescription((String) questData.get("description"));
            quest.setDifficulty((String) questData.get("difficulty"));
            quest.setQuestType((String) questData.get("questType"));
            quest.setRewardXP((Integer) questData.get("rewardXP"));
            quest.setRewardItem((String) questData.get("rewardItem"));

            return questRepository.save(quest);

        } catch (Exception e) {
            log.error("Quest generation error: {}", e.getMessage());
            return createFallbackQuest(userId);
        }
    }

    public List<Quest> getUserQuests(Long userId) {
        return questRepository.findByUserId(userId);
    }

    public Quest completeQuest(Long questId) {
        Quest quest = questRepository.findById(questId)
                .orElseThrow(() -> new RuntimeException("Quest not found"));
        quest.setCompleted(true);
        return questRepository.save(quest);
    }

    private Quest createFallbackQuest(Long userId) {
        Quest quest = new Quest();
        quest.setUserId(userId);
        quest.setTitle("The Lost Artifact");
        quest.setDescription("Find the ancient artifact hidden in the Dark Forest.");
        quest.setDifficulty("MEDIUM");
        quest.setQuestType("EXPLORE");
        quest.setRewardXP(500);
        quest.setRewardItem("Ancient Sword");
        return questRepository.save(quest);
    }
}