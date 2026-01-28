package com.example.demo.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SecurityTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void staff_can_get() throws Exception {
        mockMvc.perform(get("/api/sales/customers")
                        .header("Authorization", "Bearer dummy")
                        .header("X-ROLE", "STAFF"))
                .andExpect(status().isOk());
    }

    @Test
    void staff_cannot_delete() throws Exception {
        mockMvc.perform(delete("/api/sales/customers/1")
                        .header("Authorization", "Bearer dummy")
                        .header("X-ROLE", "STAFF"))
                .andExpect(status().isForbidden());
    }

    @Test
    void staff_cannot_post() throws Exception {
        Map<String, Object> body = new HashMap<>();
        body.put("name", "Book");
        body.put("amount", 10);

        mockMvc.perform(post("/api/sales/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body))
                        .header("Authorization", "Bearer dummy")
                        .header("X-ROLE", "STAFF"))
                .andExpect(status().isForbidden());
    }

    @Test
    void staff_cannot_put() throws Exception {
        Map<String, Object> body = new HashMap<>();
        body.put("name", "Book");
        body.put("amount", 10);

        mockMvc.perform(put("/api/sales/customers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body))
                        .header("Authorization", "Bearer dummy")
                        .header("X-ROLE", "STAFF"))
                .andExpect(status().isForbidden());
    }

    @Test
    void it_dev_cannot_read_sale_api() throws Exception {
        mockMvc.perform(get("/api/sales/customers")
                        .header("Authorization", "Bearer dummy")
                        .header("X-ROLE", "DEVELOPER"))
                .andExpect(status().isForbidden());
    }

}