package com.noteapp.storage;

import com.noteapp.model.Note;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.io.TempDir;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

class FileStorageTest {

    @TempDir
    Path tempDir;

    private FileStorage fileStorage;
    private File storageFile;

    @BeforeEach
    void setUp() {
        storageFile = tempDir.resolve("test_notes.json").toFile();
        fileStorage = new FileStorage(storageFile.getAbsolutePath());
    }

    @AfterEach
    void tearDown() {
        if (storageFile.exists()) {
            storageFile.delete();
        }
    }

    @Test
    void testSaveNote() {
        Note note = new Note("Test Title", "Test Content");
        
        assertTrue(fileStorage.saveNote(note));
        assertTrue(storageFile.exists());
    }

    @Test
    void testSaveNoteWithNullNote() {
        assertFalse(fileStorage.saveNote(null));
    }

    @Test
    void testLoadAllNotes() {
        Note note1 = new Note("Title 1", "Content 1");
        Note note2 = new Note("Title 2", "Content 2");
        
        fileStorage.saveNote(note1);
        fileStorage.saveNote(note2);
        
        List<Note> notes = fileStorage.loadAllNotes();
        
        assertEquals(2, notes.size());
        assertTrue(notes.stream().anyMatch(n -> n.getTitle().equals("Title 1")));
        assertTrue(notes.stream().anyMatch(n -> n.getTitle().equals("Title 2")));
    }

    @Test
    void testLoadAllNotesEmptyFile() {
        List<Note> notes = fileStorage.loadAllNotes();
        assertTrue(notes.isEmpty());
    }

    @Test
    void testFindNoteById() {
        Note note = new Note("Test Title", "Test Content");
        fileStorage.saveNote(note);
        
        Optional<Note> foundNote = fileStorage.findNoteById(note.getId());
        
        assertTrue(foundNote.isPresent());
        assertEquals("Test Title", foundNote.get().getTitle());
        assertEquals("Test Content", foundNote.get().getContent());
    }

    @Test
    void testFindNoteByIdNotFound() {
        Optional<Note> foundNote = fileStorage.findNoteById("non-existent-id");
        assertFalse(foundNote.isPresent());
    }

    @Test
    void testUpdateNote() {
        Note note = new Note("Original Title", "Original Content");
        fileStorage.saveNote(note);
        
        note.setTitle("Updated Title");
        note.setContent("Updated Content");
        note.setLastModified(LocalDateTime.now());
        
        assertTrue(fileStorage.updateNote(note));
        
        Optional<Note> updatedNote = fileStorage.findNoteById(note.getId());
        assertTrue(updatedNote.isPresent());
        assertEquals("Updated Title", updatedNote.get().getTitle());
        assertEquals("Updated Content", updatedNote.get().getContent());
    }

    @Test
    void testUpdateNoteNotFound() {
        Note note = new Note("Non-existent", "Content");
        note.setId("fake-id");
        
        assertFalse(fileStorage.updateNote(note));
    }

    @Test
    void testUpdateNoteWithNull() {
        assertFalse(fileStorage.updateNote(null));
    }

    @Test
    void testDeleteNote() {
        Note note = new Note("To Delete", "Content");
        fileStorage.saveNote(note);