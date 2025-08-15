package com.noteapp.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

class NoteManagerTest {

    private NoteManager noteManager;
    
    @Mock
    private NoteRepository noteRepository;
    
    private Note testNote;
    private Note testNote2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        noteManager = new NoteManager(noteRepository);
        
        testNote = new Note();
        testNote.setId(1L);
        testNote.setTitle("Test Note");
        testNote.setContent("This is a test note content");
        testNote.setCreatedAt(LocalDateTime.now());
        testNote.setUpdatedAt(LocalDateTime.now());
        
        testNote2 = new Note();
        testNote2.setId(2L);
        testNote2.setTitle("Another Test Note");
        testNote2.setContent("Another test content");
        testNote2.setCreatedAt(LocalDateTime.now().minusDays(1));
        testNote2.setUpdatedAt(LocalDateTime.now().minusDays(1));
    }

    @Test
    @DisplayName("Should create note successfully")
    void testCreateNote() {
        // Given
        String title = "New Note";
        String content = "New note content";
        when(noteRepository.save(any(Note.class))).thenReturn(testNote);

        // When
        Note createdNote = noteManager.createNote(title, content);

        // Then
        assertNotNull(createdNote);
        verify(noteRepository).save(any(Note.class));
    }

    @Test
    @DisplayName("Should throw exception when creating note with null title")
    void testCreateNoteWithNullTitle() {
        // Given
        String title = null;
        String content = "Some content";

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            noteManager.createNote(title, content);
        });
        
        verify(noteRepository, never()).save(any(Note.class));
    }

    @Test
    @DisplayName("Should throw exception when creating note with empty title")
    void testCreateNoteWithEmptyTitle() {
        // Given
        String title = "";
        String content = "Some content";

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            noteManager.createNote(title, content);
        });
        
        verify(noteRepository, never()).save(any(Note.class));
    }

    @Test
    @DisplayName("Should throw exception when creating note with null content")
    void testCreateNoteWithNullContent() {
        // Given
        String title = "Valid Title";
        String content = null;

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            noteManager.createNote(title, content);
        });
        
        verify(noteRepository, never()).save(any(Note.class));
    }

    @Test
    @DisplayName("Should get note by id successfully")
    void testGetNoteById() {
        // Given
        Long noteId = 1L;
        when(noteRepository.findById(noteId)).thenReturn(Optional.of(testNote));

        // When
        Optional<Note> foundNote = noteManager.getNoteById(noteId);

        // Then
        assertTrue(foundNote.isPresent());
        assertEquals(testNote.getId(), foundNote.get().getId());
        assertEquals(testNote.getTitle(), foundNote.get().getTitle());
        verify(noteRepository).findById(noteId);
    }

    @Test
    @DisplayName("Should return empty optional when note not found")
    void testGet