package com.noteapp.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;

@DisplayName("Note Model Tests")
public class NoteTest {

    private Note note;
    private final String SAMPLE_TITLE = "Test Note";
    private final String SAMPLE_CONTENT = "This is a test note content";
    private final String SAMPLE_CATEGORY = "Work";

    @BeforeEach
    void setUp() {
        note = new Note();
    }

    @Test
    @DisplayName("Should create note with default constructor")
    void testDefaultConstructor() {
        assertNotNull(note);
        assertNull(note.getId());
        assertNull(note.getTitle());
        assertNull(note.getContent());
        assertNull(note.getCategory());
        assertNotNull(note.getCreatedAt());
        assertNotNull(note.getUpdatedAt());
        assertFalse(note.isPinned());
        assertFalse(note.isArchived());
    }

    @Test
    @DisplayName("Should create note with parameterized constructor")
    void testParameterizedConstructor() {
        note = new Note(SAMPLE_TITLE, SAMPLE_CONTENT);
        
        assertNotNull(note);
        assertEquals(SAMPLE_TITLE, note.getTitle());
        assertEquals(SAMPLE_CONTENT, note.getContent());
        assertNotNull(note.getCreatedAt());
        assertNotNull(note.getUpdatedAt());
        assertFalse(note.isPinned());
        assertFalse(note.isArchived());
    }

    @Test
    @DisplayName("Should create note with full constructor")
    void testFullConstructor() {
        note = new Note(SAMPLE_TITLE, SAMPLE_CONTENT, SAMPLE_CATEGORY);
        
        assertNotNull(note);
        assertEquals(SAMPLE_TITLE, note.getTitle());
        assertEquals(SAMPLE_CONTENT, note.getContent());
        assertEquals(SAMPLE_CATEGORY, note.getCategory());
        assertNotNull(note.getCreatedAt());
        assertNotNull(note.getUpdatedAt());
        assertFalse(note.isPinned());
        assertFalse(note.isArchived());
    }

    @Test
    @DisplayName("Should set and get id correctly")
    void testIdGetterSetter() {
        Long testId = 1L;
        note.setId(testId);
        assertEquals(testId, note.getId());
    }

    @Test
    @DisplayName("Should set and get title correctly")
    void testTitleGetterSetter() {
        note.setTitle(SAMPLE_TITLE);
        assertEquals(SAMPLE_TITLE, note.getTitle());
    }

    @Test
    @DisplayName("Should set and get content correctly")
    void testContentGetterSetter() {
        note.setContent(SAMPLE_CONTENT);
        assertEquals(SAMPLE_CONTENT, note.getContent());
    }

    @Test
    @DisplayName("Should set and get category correctly")
    void testCategoryGetterSetter() {
        note.setCategory(SAMPLE_CATEGORY);
        assertEquals(SAMPLE_CATEGORY, note.getCategory());
    }

    @Test
    @DisplayName("Should set and get pinned status correctly")
    void testPinnedGetterSetter() {
        note.setPinned(true);
        assertTrue(note.isPinned());
        
        note.setPinned(false);
        assertFalse(note.isPinned());
    }

    @Test
    @DisplayName("Should set and get archived status correctly")
    void testArchivedGetterSetter() {
        note.setArchived(true);
        assertTrue(note.isArchived());
        
        note.setArchived(false);
        assertFalse(note.isArchived());
    }

    @Test
    @DisplayName("Should set and get created date correctly")
    void testCreatedAtGetter