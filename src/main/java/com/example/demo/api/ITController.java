package com.example.demo.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/it/tickets")
public class ITController {

    @GetMapping
    public ResponseEntity<Map<String, String>> getAll() {
        return ResponseEntity.ok(Map.of("message", "IT Department - Get all tickets"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getById(@PathVariable String id) {
        return ResponseEntity.ok(Map.of(
            "message", "IT Department - Get ticket by ID",
            "id", id
        ));
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> create(@RequestBody Map<String, Object> request) {
        return ResponseEntity.ok(Map.of("message", "IT Department - Create ticket"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable String id, @RequestBody Map<String, Object> request) {
        return ResponseEntity.ok(Map.of(
            "message", "IT Department - Update ticket",
            "id", id
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable String id) {
        return ResponseEntity.ok(Map.of(
            "message", "IT Department - Delete ticket",
            "id", id
        ));
    }
}

