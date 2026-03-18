package com.example.demo.controller;

import com.example.demo.model.Document;
import com.example.demo.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    private final DocumentService documentService;

    @Autowired
    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasPermission(#id, 'Document', 'READ')")
    public Document get(@PathVariable Long id) {
        return documentService.get(id);
    }

    @PostMapping
    public Document create(@RequestBody Document doc) {
        return documentService.create(doc);
    }
}
