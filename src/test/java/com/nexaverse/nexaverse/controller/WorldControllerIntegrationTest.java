package com.nexaverse.nexaverse.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexaverse.nexaverse.dto.WorldDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class WorldControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllWorlds_ReturnsOk() throws Exception {
        mockMvc.perform(get("/api/worlds"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    void createWorld_ValidData_ReturnsCreated() throws Exception {
        WorldDTO dto = new WorldDTO();
        dto.setName("Test World " + System.currentTimeMillis());
        dto.setWorldType("FOREST");
        dto.setMaxPlayers(50);
        dto.setActive(true);

        mockMvc.perform(post("/api/worlds")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.name").value(dto.getName()));
    }

    @Test
    void createWorld_InvalidData_ReturnsBadRequest() throws Exception {
        WorldDTO dto = new WorldDTO();
        // name missing — validation fail hoga

        mockMvc.perform(post("/api/worlds")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getWorldById_NotFound_Returns404() throws Exception {
        mockMvc.perform(get("/api/worlds/99999"))
                .andExpect(status().isNotFound());
    }
}