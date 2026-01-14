package com.example.demo.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/marketing/campaigns")
public class MarketingController {

    @GetMapping
    public ResponseEntity<Map<String, String>> getAll() {
        return ResponseEntity.ok(Map.of("message", "Marketing Department - Get all campaigns"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getById(@PathVariable String id) {
        return ResponseEntity.ok(Map.of(
            "message", "Marketing Department - Get campaign by ID",
            "id", id
        ));
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> create(@RequestBody Map<String, Object> request) {
        return ResponseEntity.ok(Map.of("message", "Marketing Department - Create campaign"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable String id, @RequestBody Map<String, Object> request) {
        return ResponseEntity.ok(Map.of(
            "message", "Marketing Department - Update campaign",
            "id", id
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable String id) {
        return ResponseEntity.ok(Map.of(
            "message", "Marketing Department - Delete campaign",
            "id", id
        ));
    }
}

