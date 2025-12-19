package com.example.myThoughts.service;

import com.example.myThoughts.model.Note;
import com.example.myThoughts.repository.NoteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
public class NoteService {
    @Autowired
    private NoteRepository repository;

    public String saveNote(String content) {
        Note note = new Note(content);
        repository.save(note);
        return note.getId();
    }

    @Transactional
    public String getOrUpdateExpiry(String id) {
        Optional<Note> noteOpt = repository.findById(id);
        if (noteOpt.isEmpty()) return null;

        Note note = noteOpt.get();
        Instant now = Instant.now();

        // Condition 1: If it was NEVER opened before
        if (note.getFirstOpenedAt() == null) {
            // Mark the opening time
            note.setFirstOpenedAt(now);
            repository.saveAndFlush(note);
            return note.getEncryptedContent();
        }

        // Condition 2: If it was ALREADY opened (Refresh)
        // OR if it's past 5 minutes since the first opening
        if (now.isAfter(note.getFirstOpenedAt().plus(5, ChronoUnit.MINUTES)) || note.getFirstOpenedAt() != null) {
            repository.delete(note);
            repository.flush();
            return null;
        }

        return note.getEncryptedContent();
    }
}