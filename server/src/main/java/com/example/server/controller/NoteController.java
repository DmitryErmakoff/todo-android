package com.example.server.controller;

import com.example.server.entity.Note;
import com.example.server.service.GPIOService;
import com.example.server.service.NoteService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/notes")
public class NoteController {
    private final NoteService noteService;
    private final GPIOService gpioService;

    @GetMapping
    public List<Note> getAllNotes() {
        return noteService.getAllNotes();
    }

    @PostMapping
    public ResponseEntity<Note> createNote(@Valid @RequestBody Note note) {
        gpioService.blinkLed();
        return new ResponseEntity<>(noteService.createNote(note), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable Long id) {
        gpioService.blinkLed();
        noteService.deleteNote(id);
        return ResponseEntity.noContent().build();
    }
}
