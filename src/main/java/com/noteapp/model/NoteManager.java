package com.noteapp.model;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class NoteManager {
    private List<Note> notes;
    private int nextId;
    
    public NoteManager() {
        this.notes = new ArrayList<>();
        this.nextId = 1;
    }
    
    /**
     * Creates a new note with the given title and content
     * @param title The title of the note
     * @param content The content of the note
     * @return The created note
     */
    public Note createNote(String title, String content) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Note title cannot be null or empty");
        }
        
        Note note = new Note(nextId++, title.trim(), content != null ? content.trim() : "");
        notes.add(note);
        return note;
    }
    
    /**
     * Retrieves a note by its ID
     * @param id The ID of the note
     * @return The note with the specified ID, or null if not found
     */
    public Note getNoteById(int id) {
        return notes.stream()
                .filter(note -> note.getId() == id)
                .findFirst()
                .orElse(null);
    }
    
    /**
     * Retrieves all notes
     * @return A list of all notes
     */
    public List<Note> getAllNotes() {
        return new ArrayList<>(notes);
    }
    
    /**
     * Updates an existing note
     * @param id The ID of the note to update
     * @param title The new title
     * @param content The new content
     * @return true if the note was updated, false if not found
     */
    public boolean updateNote(int id, String title, String content) {
        Note note = getNoteById(id);
        if (note == null) {
            return false;
        }
        
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Note title cannot be null or empty");
        }
        
        note.setTitle(title.trim());
        note.setContent(content != null ? content.trim() : "");
        note.setLastModified(LocalDateTime.now());
        return true;
    }
    
    /**
     * Deletes a note by its ID
     * @param id The ID of the note to delete
     * @return true if the note was deleted, false if not found
     */
    public boolean deleteNote(int id) {
        return notes.removeIf(note -> note.getId() == id);
    }
    
    /**
     * Searches for notes containing the specified query in title or content
     * @param query The search query
     * @return A list of notes matching the query
     */
    public List<Note> searchNotes(String query) {
        if (query == null || query.trim().isEmpty()) {
            return getAllNotes();
        }
        
        String lowercaseQuery = query.toLowerCase().trim();
        return notes.stream()
                .filter(note -> 
                    note.getTitle().toLowerCase().contains(lowercaseQuery) ||
                    note.getContent().toLowerCase().contains(lowercaseQuery))
                .collect(Collectors.toList());
    }
    
    /**
     * Gets notes sorted by creation date (newest first)
     * @return A list of notes sorted by creation date
     */
    public List<Note> getNotesByCreationDate() {
        return notes.stream()
                .sorted((n1, n2) -> n2.getCreatedAt().compareTo(n1.getCreatedAt()))
                .collect(Collectors.toList());
    }
    
    /**
     * Gets notes sorted by last modified date (newest first)
     * @return A list of notes sorted by last modified date
     */
    public List<Note> getNotesByModifiedDate() {
        return notes.stream()
                .sorted((n1, n2) -> n2.getLastModified().compareTo(n1.getLastModified()))
                .collect(Collectors.toList());