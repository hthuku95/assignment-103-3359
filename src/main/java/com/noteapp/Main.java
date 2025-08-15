package com.noteapp;

import com.noteapp.ui.MainWindow;
import com.noteapp.service.NoteService;
import com.noteapp.repository.FileNoteRepository;
import com.noteapp.util.ConfigManager;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.io.File;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Main entry point for the Note Taking Application
 * Initializes the application components and launches the GUI
 */
public class Main {
    
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    
    public static void main(String[] args) {
        try {
            // Set system look and feel for better native appearance
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeel());
        } catch (ClassNotFoundException | InstantiationException | 
                 IllegalAccessException | UnsupportedLookAndFeelException e) {
            LOGGER.log(Level.WARNING, "Failed to set system look and feel", e);
        }
        
        // Initialize application on Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            try {
                initializeApplication();
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Failed to initialize application", e);
                System.exit(1);
            }
        });
    }
    
    /**
     * Initializes the application components and creates the main window
     */
    private static void initializeApplication() {
        LOGGER.info("Starting Note Taking Application...");
        
        try {
            // Initialize configuration
            ConfigManager configManager = ConfigManager.getInstance();
            
            // Create data directory if it doesn't exist
            String dataDir = configManager.getDataDirectory();
            File dataDirFile = new File(dataDir);
            if (!dataDirFile.exists()) {
                boolean created = dataDirFile.mkdirs();
                if (!created) {
                    throw new RuntimeException("Failed to create data directory: " + dataDir);
                }
                LOGGER.info("Created data directory: " + dataDir);
            }
            
            // Initialize repository
            FileNoteRepository noteRepository = new FileNoteRepository(dataDir);
            
            // Initialize service layer
            NoteService noteService = new NoteService(noteRepository);
            
            // Create and show main window
            MainWindow mainWindow = new MainWindow(noteService);
            mainWindow.setVisible(true);
            
            LOGGER.info("Note Taking Application started successfully");
            
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error during application initialization", e);
            throw new RuntimeException("Failed to initialize application", e);
        }
    }
}