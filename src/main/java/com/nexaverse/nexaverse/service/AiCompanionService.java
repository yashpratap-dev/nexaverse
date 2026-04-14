package com.nexaverse.nexaverse.service;

import com.nexaverse.nexaverse.entity.AiCompanion;
import com.nexaverse.nexaverse.repository.AiCompanionRepository;
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
public class AiCompanionService {

    private final AiCompanionRepository aiCompanionRepository;
    private final RestTemplate restTemplate;

    @Value("${GROQ_API_KEY}")
    private String groqApiKey;

    private static final String GROQ_URL = "https://api.groq.com/openai/v1/chat/completions";
    private static final String MODEL = "llama-3.3-70b-versatile";

    private static final String MIMIR_PERSONALITY = """
        You are MIMIR, the wisest being in NexaVerse.
        You speak like the Norse god Mimir from God of War — wise, ancient,
        slightly sarcastic but always helpful. You know everything about
        the NexaVerse world, its history, and guide warriors on their journey.
        Keep responses concise (2-3 sentences max).
        """;

    private static final String GUANYIN_PERSONALITY = """
        You are GUANYIN, the goddess of mercy and wisdom in NexaVerse.
        You speak like the guide from Black Myth: Wukong — calm,
        deep, spiritual and strategic. You help warriors find their
        inner strength and navigate the NexaVerse world with wisdom.
        Keep responses concise (2-3 sentences max).
        """;

    public String chat(Long userId, String userMessage) {
        AiCompanion companion = aiCompanionRepository.findByUserId(userId)
                .orElseGet(() -> createDefaultCompanion(userId));

        String personality = getPersonality(companion);

        // Groq API direct call
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(groqApiKey);

        Map<String, Object> systemMessage = Map.of(
                "role", "system",
                "content", personality
        );
        Map<String, Object> userMsg = Map.of(
                "role", "user",
                "content", userMessage
        );

        Map<String, Object> body = new HashMap<>();
        body.put("model", MODEL);
        body.put("messages", List.of(systemMessage, userMsg));
        body.put("max_tokens", 500);
        body.put("temperature", 0.8);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(
                    GROQ_URL, request, Map.class);

            Map<String, Object> responseBody = response.getBody();
            List<Map<String, Object>> choices =
                    (List<Map<String, Object>>) responseBody.get("choices");
            Map<String, Object> message =
                    (Map<String, Object>) choices.get(0).get("message");
            String content = (String) message.get("content");

            log.info("AI Companion {} responded", companion.getName());
            return content;
        } catch (Exception e) {
            log.error("Groq API error: {}", e.getMessage());
            return "The ancient wisdom is temporarily unavailable. Try again, warrior.";
        }
    }

    public AiCompanion selectCompanion(Long userId, String companionType, String customName) {
        AiCompanion companion = aiCompanionRepository.findByUserId(userId)
                .orElse(new AiCompanion());

        companion.setUserId(userId);
        companion.setCompanionType(companionType.toUpperCase());

        switch (companionType.toUpperCase()) {
            case "MIMIR" -> {
                companion.setName("MIMIR");
                companion.setPersonality(MIMIR_PERSONALITY);
            }
            case "GUANYIN" -> {
                companion.setName("GUANYIN");
                companion.setPersonality(GUANYIN_PERSONALITY);
            }
            default -> {
                companion.setName(customName != null ? customName : "NEXUS");
                companion.setCustomName(customName);
                companion.setPersonality("You are " + customName +
                        ", a wise and helpful AI companion in NexaVerse.");
            }
        }
        return aiCompanionRepository.save(companion);
    }

    private AiCompanion createDefaultCompanion(Long userId) {
        AiCompanion companion = new AiCompanion();
        companion.setUserId(userId);
        companion.setName("MIMIR");
        companion.setCompanionType("MIMIR");
        companion.setPersonality(MIMIR_PERSONALITY);
        return aiCompanionRepository.save(companion);
    }

    private String getPersonality(AiCompanion companion) {
        return switch (companion.getCompanionType()) {
            case "MIMIR" -> MIMIR_PERSONALITY;
            case "GUANYIN" -> GUANYIN_PERSONALITY;
            default -> companion.getPersonality();
        };
    }
}