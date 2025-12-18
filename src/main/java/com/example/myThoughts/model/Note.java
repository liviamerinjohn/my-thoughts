package com.example.myThoughts.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

import java.time.Instant;
import java.util.UUID;

@Entity
public class Note {
    @Id
    private String id = UUID.randomUUID().toString();

    @Lob
    @Column(columnDefinition = "TEXT")
    private String encryptedContent;

    private  Instant firstOpenedAt; // Tracks the start of the 1-hour window

    public Note() {}
    public Note(String content) {
        this.encryptedContent = content;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEncryptedContent(String encryptedContent) {
        this.encryptedContent = encryptedContent;
    }

    // Getters and Setters
    public String getId() { return id; }
    public String getEncryptedContent() { return encryptedContent; }
    public Instant getFirstOpenedAt() { return firstOpenedAt; }
    public void setFirstOpenedAt(Instant firstOpenedAt) { this.firstOpenedAt = firstOpenedAt; }
}