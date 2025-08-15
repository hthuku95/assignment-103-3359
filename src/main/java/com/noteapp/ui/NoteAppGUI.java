package com.noteapp.ui;

import com.noteapp.model.Note;
import com.noteapp.model.NoteManager;
import com.noteapp.util.FileManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class NoteAppGUI extends JFrame {
    private static final long serialVersionUID = 1L;
    
    // Core components
    private NoteManager noteManager;
    private FileManager fileManager;
    
    // GUI Components
    private JList<Note> noteList;
    private DefaultListModel<Note> listModel;
    private JTextArea contentArea;
    private JTextField titleField;
    private JLabel statusLabel;
    private JButton saveButton;
    private JButton deleteButton;
    private JButton newButton;
    
    // Current state
    private Note currentNote;
    private boolean isModified = false;
    
    public NoteAppGUI() {
        initializeManagers();
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        setupMenuBar();
        setupToolBar();
        loadNotes();
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Note Taking Application");
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    private void initializeManagers() {
        noteManager = new NoteManager();
        fileManager = new FileManager();
    }
    
    private void initializeComponents() {
        // Initialize list components
        listModel = new DefaultListModel<>();
        noteList = new JList<>(listModel);
        noteList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        noteList.setCellRenderer(new NoteListCellRenderer());
        
        // Initialize text components
        titleField = new JTextField();
        titleField.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        titleField.setBorder(BorderFactory.createTitledBorder("Title"));
        
        contentArea = new JTextArea();
        contentArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        contentArea.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // Initialize buttons
        newButton = new JButton("New Note");
        saveButton = new JButton("Save");
        deleteButton = new JButton("Delete");
        
        // Initialize status label
        statusLabel = new JLabel("Ready");
        statusLabel.setBorder(new EmptyBorder(5, 10, 5, 10));
        
        // Set initial states
        saveButton.setEnabled(false);
        deleteButton.setEnabled(false);
        titleField.setEnabled(false);
        contentArea.setEnabled(false);
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Left panel - Note list
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setPreferredSize(new Dimension(300, 0));
        leftPanel.setBorder(BorderFactory.createTitledBorder("Notes"));
        
        JScrollPane listScrollPane = new JScrollPane(noteList);
        leftPanel.add(listScrollPane, BorderLayout.CENTER);
        
        // Right panel - Note editor
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBorder(new EmptyBorder(10, 10, 10, 10));