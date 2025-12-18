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
        if (noteOpt.isEmpty()) {
            return null;
        }

        Note note = noteOpt.get();
        Instant now = Instant.now();

        // 1. If it's the first time being opened
        if (note.getFirstOpenedAt() == null) {
            note.setFirstOpenedAt(now);
            repository.save(note); // Start the timer
            return note.getEncryptedContent();
        }

        // 2. If already opened, check if 5 minutes have passed
        // We use ChronoUnit.MINUTES to check the difference between the two Instants
        long minutesElapsed = ChronoUnit.MINUTES.between(note.getFirstOpenedAt(), now);

        if (minutesElapsed >= 30) {
            repository.delete(note); // Expired
            return null;
        }

        return note.getEncryptedContent();
    }
}