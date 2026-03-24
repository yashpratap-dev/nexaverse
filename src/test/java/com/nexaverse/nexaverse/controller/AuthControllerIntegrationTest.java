package com.nexaverse.nexaverse.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexaverse.nexaverse.dto.UserDTO;
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
class AuthControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void register_ValidUser_ReturnsSuccess() throws Exception {
        UserDTO dto = new UserDTO();
        dto.setUsername("testuser" + System.currentTimeMillis());
        dto.setEmail("test" + System.currentTimeMillis() + "@gmail.com");
        dto.setPassword("password123");
        dto.setAvatarName("TestAvatar");

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    void register_InvalidEmail_ReturnsBadRequest() throws Exception {
        UserDTO dto = new UserDTO();
        dto.setUsername("testuser");
        dto.setEmail("not-an-email");
        dto.setPassword("password123");
        dto.setAvatarName("TestAvatar");

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void login_InvalidCredentials_ReturnsUnauthorized() throws Exception {
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"wronguser\",\"password\":\"wrongpass\"}"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void hello_ReturnsAlive() throws Exception {
        mockMvc.perform(get("/hello"))
                .andExpect(status().isOk());
    }
}