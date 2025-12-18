package com.example.myThoughts.repository;

import com.example.myThoughts.model.Note;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends CrudRepository<Note, String> {
}
