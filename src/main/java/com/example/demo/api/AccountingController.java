package com.example.demo.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/accounting/salaries")
public class AccountingController {

    @GetMapping
    public ResponseEntity<Map<String, String>> getAll() {
        return ResponseEntity.ok(Map.of("message", "Accounting Department - Get all transactions"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getById(@PathVariable String id) {
        return ResponseEntity.ok(Map.of(
            "message", "Accounting Department - Get transaction by ID",
            "id", id
        ));
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> create(@RequestBody Map<String, Object> request) {
        return ResponseEntity.ok(Map.of("message", "Accounting Department - Create transaction"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable String id, @RequestBody Map<String, Object> request) {
        return ResponseEntity.ok(Map.of(
            "message", "Accounting Department - Update transaction",
            "id", id
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable String id) {
        return ResponseEntity.ok(Map.of(
            "message", "Accounting Department - Delete transaction",
            "id", id
        ));
    }
}

