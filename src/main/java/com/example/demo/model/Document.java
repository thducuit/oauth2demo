package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Document {
    @Id
    Long id;
    String title;
    String content;
    String owner;
}
