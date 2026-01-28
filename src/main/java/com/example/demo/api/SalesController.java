package com.example.demo.api;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/sales/customers")
@PreAuthorize("hasAnyRole('MANAGER', 'CSO')")
public class SalesController {

    @GetMapping
    @PreAuthorize("hasAnyRole('STAFF', 'LEADER')")
    public ResponseEntity<Map<String, String>> getAll() {
        return ResponseEntity.ok(Map.of("message", "Sales Department - Get all deals"));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('STAFF', 'LEADER')")
    public ResponseEntity<Map<String, Object>> getById(@PathVariable String id) {
        return ResponseEntity.ok(Map.of(
            "message", "Sales Department - Get deal by ID",
            "id", id
        ));
    }

    @PostMapping
    @PreAuthorize("hasRole('LEADER')")
    public ResponseEntity<Map<String, String>> create(@RequestBody Map<String, Object> request) {
        return ResponseEntity.ok(Map.of("message", "Sales Department - Create deal"));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('LEADER')")
    public ResponseEntity<Map<String, Object>> update(@PathVariable String id, @RequestBody Map<String, Object> request) {
        return ResponseEntity.ok(Map.of(
            "message", "Sales Department - Update deal",
            "id", id
        ));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('LEADER')")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable String id) {
        return ResponseEntity.ok(Map.of(
            "message", "Sales Department - Delete deal",
            "id", id
        ));
    }
}

