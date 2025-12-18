package com.example.myThoughts.controller;

import com.example.myThoughts.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notes")
public class NoteController {
    @Autowired
    private NoteService noteService;

    @PostMapping
    public String createNote(@RequestBody String encryptedContent) {
        return noteService.saveNote(encryptedContent);
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getNote(@PathVariable String id) {
        String content = noteService.getOrUpdateExpiry(id);
        if (content == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(content);
    }
}
