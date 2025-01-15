package com.example.server;

import com.example.server.controller.NoteController;
import com.example.server.entity.Note;
import com.example.server.service.NoteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class NoteTest {

    private MockMvc mockMvc;

    @Mock
    private NoteService noteService;

    @InjectMocks
    private NoteController noteController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(noteController).build();
    }

    @Test
    public void testGetAllNotes() throws Exception {
        Note note1 = Note.builder().id(1L).title("Note 1").date("2023-10-01").time("10:00").build();
        Note note2 = Note.builder().id(2L).title("Note 2").date("2023-10-02").time("11:00").build();
        List<Note> notes = Arrays.asList(note1, note2);

        when(noteService.getAllNotes()).thenReturn(notes);

        mockMvc.perform(get("/api/notes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Note 1"))
                .andExpect(jsonPath("$[1].title").value("Note 2"));

        verify(noteService, times(1)).getAllNotes();
    }

    @Test
    public void testCreateNote() throws Exception {
        Note note = Note.builder().id(1L).title("New Note").date("2023-10-03").time("12:00").build();
        when(noteService.createNote(any(Note.class))).thenReturn(note);

        mockMvc.perform(post("/api/notes")
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(note)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("New Note"));

        ArgumentCaptor<Note> noteCaptor = ArgumentCaptor.forClass(Note.class);
        verify(noteService, times(1)).createNote(noteCaptor.capture());
        assertEquals("New Note", noteCaptor.getValue().getTitle());
    }

    @Test
    public void testDeleteNote() throws Exception {
        Long noteId = 1L;

        mockMvc.perform(delete("/api/notes/{id}", noteId))
                .andExpect(status().isNoContent());

        verify(noteService, times(1)).deleteNote(noteId);
    }
}
