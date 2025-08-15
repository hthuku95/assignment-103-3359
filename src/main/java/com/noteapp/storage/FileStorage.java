package com.noteapp.storage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.noteapp.model.Note;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileStorage {
    private static final Logger LOGGER = Logger.getLogger(FileStorage.class.getName());
    private static final String STORAGE_DIR = "notes";
    private static final String NOTES_FILE = "notes.json";
    
    private final ObjectMapper objectMapper;
    private final Path storageDirectory;
    private final Path notesFilePath;
    
    public FileStorage() {
        this.objectMapper = new ObjectMapper();
        this.storageDirectory = Paths.get(STORAGE_DIR);
        this.notesFilePath = storageDirectory.resolve(NOTES_FILE);
        initializeStorage();
    }
    
    public FileStorage(String customStorageDir) {
        this.objectMapper = new ObjectMapper();
        this.storageDirectory = Paths.get(customStorageDir);
        this.notesFilePath = storageDirectory.resolve(NOTES_FILE);
        initializeStorage();
    }
    
    private void initializeStorage() {
        try {
            if (!Files.exists(storageDirectory)) {
                Files.createDirectories(storageDirectory);
                LOGGER.info("Created storage directory: " + storageDirectory);
            }
            
            if (!Files.exists(notesFilePath)) {
                saveNotes(new ArrayList<>());
                LOGGER.info("Created notes file: " + notesFilePath);
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to initialize storage", e);
            throw new RuntimeException("Cannot initialize file storage", e);
        }
    }
    
    public List<Note> loadNotes() {
        try {
            if (!Files.exists(notesFilePath) || Files.size(notesFilePath) == 0) {
                return new ArrayList<>();
            }
            
            CollectionType listType = objectMapper.getTypeFactory()
                    .constructCollectionType(List.class, Note.class);
            List<Note> notes = objectMapper.readValue(notesFilePath.toFile(), listType);
            
            LOGGER.info("Loaded " + notes.size() + " notes from storage");
            return notes != null ? notes : new ArrayList<>();
            
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to load notes from file", e);
            return new ArrayList<>();
        }
    }
    
    public boolean saveNotes(List<Note> notes) {
        if (notes == null) {
            notes = new ArrayList<>();
        }
        
        try {
            objectMapper.writerWithDefaultPrettyPrinter()
                    .writeValue(notesFilePath.toFile(), notes);
            
            LOGGER.info("Saved " + notes.size() + " notes to storage");
            return true;
            
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to save notes to file", e);
            return false;
        }
    }
    
    public boolean saveNote(Note note) {
        if (note == null) {
            LOGGER.warning("Attempted to save null note");
            return false;
        }
        
        List<Note> notes = loadNotes();
        
        // Check if note already exists and update it
        boolean updated = false;
        for (int i = 0; i < notes.size(); i++) {
            if (notes.get(i).getId().equals(note.getId())) {
                notes.set(i, note);
                updated = true;
                break;
            }
        }
        
        // If not updated, add as new note
        if (!updated)