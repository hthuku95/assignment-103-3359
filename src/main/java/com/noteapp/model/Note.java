package com.noteapp.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.UUID;

/**
 * Note data model class representing a single note in the application.
 * Contains properties for title, content, creation date, and modification date.
 */
public class Note {
    private String id;
    private String title;
    private String content;
    private LocalDateTime creationDate;
    private LocalDateTime modificationDate;

    /**
     * Default constructor that initializes a new note with current timestamp.
     */
    public Note() {
        this.id = UUID.randomUUID().toString();
        this.title = "";
        this.content = "";
        this.creationDate = LocalDateTime.now();
        this.modificationDate = LocalDateTime.now();
    }

    /**
     * Constructor with title and content parameters.
     * 
     * @param title The title of the note
     * @param content The content of the note
     */
    public Note(String title, String content) {
        this();
        this.title = title != null ? title : "";
        this.content = content != null ? content : "";
    }

    /**
     * Full constructor with all parameters.
     * 
     * @param id The unique identifier for the note
     * @param title The title of the note
     * @param content The content of the note
     * @param creationDate The creation timestamp
     * @param modificationDate The last modification timestamp
     */
    public Note(String id, String title, String content, LocalDateTime creationDate, LocalDateTime modificationDate) {
        this.id = id != null ? id : UUID.randomUUID().toString();
        this.title = title != null ? title : "";
        this.content = content != null ? content : "";
        this.creationDate = creationDate != null ? creationDate : LocalDateTime.now();
        this.modificationDate = modificationDate != null ? modificationDate : LocalDateTime.now();
    }

    /**
     * Gets the unique identifier of the note.
     * 
     * @return The note ID
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the note.
     * 
     * @param id The note ID to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the title of the note.
     * 
     * @return The note title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the note and updates modification date.
     * 
     * @param title The title to set
     */
    public void setTitle(String title) {
        this.title = title != null ? title : "";
        this.modificationDate = LocalDateTime.now();
    }

    /**
     * Gets the content of the note.
     * 
     * @return The note content
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets the content of the note and updates modification date.
     * 
     * @param content The content to set
     */
    public void setContent(String content) {
        this.content = content != null ? content : "";
        this.modificationDate = LocalDateTime.now();
    }

    /**
     * Gets the creation date of the note.
     * 
     * @return The creation timestamp
     */
    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    /**
     * Sets the creation date of the note.
     * 
     * @param creationDate The creation timestamp to set
     */
    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * Gets the modification date of the note.
     * 
     * @return The last modification timestamp
     */
    public LocalDateTime getModificationDate() {
        return modificationDate;
    }

    /**
     * Sets the modification date of the note.
     * 
     * @param modificationDate The modification timestamp to set