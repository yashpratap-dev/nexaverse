package com.nexaverse.nexaverse.service;

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
public class VoiceCommandService {

    private final RestTemplate restTemplate;

    @Value("${groq.api.key}")
    private String groqApiKey;

    private static final String GROQ_URL =
            "https://api.groq.com/openai/v1/chat/completions";

    public Map<String, Object> processCommand(String voiceText, Long userId) {
        String prompt = String.format("""
            You are a NexaVerse command processor. Parse this voice command and respond ONLY with JSON:
            
            Voice command: "%s"
            
            Identify the action and respond with:
            {
              "action": "JOIN_WORLD or LEAVE_WORLD or MOVE_AVATAR or SEND_CHAT or GET_QUEST or ATTACK or HEAL or SHOW_MAP or UNKNOWN",
              "parameters": {
                "worldName": "if joining a world",
                "message": "if sending chat",
                "direction": "if moving (NORTH/SOUTH/EAST/WEST)",
                "x": number,
                "y": number
              },
              "response": "MIMIR style response to the command (1 sentence)"
            }
            """, voiceText);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(groqApiKey);

        Map<String, Object> body = new HashMap<>();
        body.put("model", "llama-3.3-70b-versatile");
        body.put("messages", List.of(
                Map.of("role", "user", "content", prompt)));
        body.put("max_tokens", 300);
        body.put("temperature", 0.3);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(
                    GROQ_URL, new HttpEntity<>(body, headers), Map.class);

            List<Map<String, Object>> choices =
                    (List<Map<String, Object>>) response.getBody().get("choices");
            String content = (String)
                    ((Map<String, Object>) choices.get(0).get("message")).get("content");

            content = content.trim()
                    .replaceAll("```json", "")
                    .replaceAll("```", "")
                    .trim();

            com.fasterxml.jackson.databind.ObjectMapper mapper =
                    new com.fasterxml.jackson.databind.ObjectMapper();
            Map<String, Object> result = mapper.readValue(content, Map.class);
            result.put("originalCommand", voiceText);
            result.put("userId", userId);

            log.info("Voice command processed: {} → {}", voiceText, result.get("action"));
            return result;

        } catch (Exception e) {
            log.error("Voice command error: {}", e.getMessage());
            return Map.of(
                    "action", "UNKNOWN",
                    "originalCommand", voiceText,
                    "response", "Speak clearly, warrior. MIMIR did not understand.",
                    "parameters", Map.of()
            );
        }
    }
}