package com.nexaverse.nexaverse.service;

import com.nexaverse.nexaverse.entity.WorldRoomEntity;
import com.nexaverse.nexaverse.repository.WorldRepository;
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
public class SemanticSearchService {

    private final RestTemplate restTemplate;
    private final WorldRepository worldRepository;

    @Value("${groq.api.key}")
    private String groqApiKey;

    private static final String GROQ_URL =
            "https://api.groq.com/openai/v1/chat/completions";

    public List<WorldRoomEntity> semanticSearch(String query) {
        List<WorldRoomEntity> allWorlds = worldRepository.findAll();

        if (allWorlds.isEmpty()) {
            return Collections.emptyList();
        }

        // Build world descriptions
        StringBuilder worldsInfo = new StringBuilder();
        for (WorldRoomEntity world : allWorlds) {
            worldsInfo.append(String.format(
                    "ID:%d Name:%s Type:%s Players:%d/%d Active:%s\n",
                    world.getId(), world.getName(), world.getWorldType(),
                    world.getCurrentPlayers(), world.getMaxPlayers(),
                    world.isActive() ? "yes" : "no"
            ));
        }

        String prompt = String.format("""
            User is searching for: "%s"
            
            Available NexaVerse worlds:
            %s
            
            Return ONLY a JSON array of world IDs that best match the search.
            Example: [1, 3, 2]
            Order by relevance. Return empty array [] if no match.
            """, query, worldsInfo);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(groqApiKey);

        Map<String, Object> body = new HashMap<>();
        body.put("model", "llama-3.3-70b-versatile");
        body.put("messages", List.of(
                Map.of("role", "user", "content", prompt)));
        body.put("max_tokens", 100);
        body.put("temperature", 0.1);

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
            List<Integer> worldIds = mapper.readValue(content, List.class);

            List<WorldRoomEntity> result = new ArrayList<>();
            for (Integer id : worldIds) {
                allWorlds.stream()
                        .filter(w -> w.getId().equals(Long.valueOf(id)))
                        .findFirst()
                        .ifPresent(result::add);
            }

            log.info("Semantic search '{}' found {} worlds", query, result.size());
            return result;

        } catch (Exception e) {
            log.error("Semantic search error: {}", e.getMessage());
            return allWorlds;
        }
    }

    public Map<String, Object> getWorldRecommendation(String mood, Long userId) {
        List<WorldRoomEntity> allWorlds = worldRepository.findAll();

        StringBuilder worldsInfo = new StringBuilder();
        for (WorldRoomEntity world : allWorlds) {
            worldsInfo.append(String.format(
                    "ID:%d Name:%s Type:%s\n",
                    world.getId(), world.getName(), world.getWorldType()
            ));
        }

        String prompt = String.format("""
            User mood/preference: "%s"
            
            Available worlds:
            %s
            
            Recommend the BEST world and explain why in MIMIR style (1-2 sentences).
            Respond ONLY with JSON:
            {
              "worldId": number,
              "worldName": "name",
              "reason": "MIMIR style explanation"
            }
            """, mood, worldsInfo);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(groqApiKey);

        Map<String, Object> body = new HashMap<>();
        body.put("model", "llama-3.3-70b-versatile");
        body.put("messages", List.of(
                Map.of("role", "user", "content", prompt)));
        body.put("max_tokens", 200);
        body.put("temperature", 0.7);

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
            return mapper.readValue(content, Map.class);

        } catch (Exception e) {
            log.error("Recommendation error: {}", e.getMessage());
            return Map.of(
                    "worldId", 1,
                    "worldName", "Dark Forest",
                    "reason", "All paths lead to the forest, warrior."
            );
        }
    }
}