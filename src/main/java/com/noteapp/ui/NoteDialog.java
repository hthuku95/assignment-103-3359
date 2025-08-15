package com.noteapp.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import com.noteapp.model.Note;

public class NoteDialog extends JDialog {
    private JTextField titleField;
    private JTextArea contentArea;
    private JButton saveButton;
    private JButton cancelButton;
    private Note note;
    private boolean saved = false;
    
    public NoteDialog(Frame parent, Note note) {
        super(parent, note == null ? "New Note" : "Edit Note", true);
        this.note = note;
        initializeComponents();
        layoutComponents();
        setupEventHandlers();
        loadNoteData();
        setupDialog();
    }
    
    private void initializeComponents() {
        titleField = new JTextField(30);
        titleField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        
        contentArea = new JTextArea(15, 40);
        contentArea.setFont(new Font("SansSerif", Font.PLAIN, 12);
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        contentArea.setTabSize(4);
        
        saveButton = new JButton("Save");
        saveButton.setPreferredSize(new Dimension(80, 30));
        
        cancelButton = new JButton("Cancel");
        cancelButton.setPreferredSize(new Dimension(80, 30));
        
        // Set mnemonics for keyboard accessibility
        saveButton.setMnemonic(KeyEvent.VK_S);
        cancelButton.setMnemonic(KeyEvent.VK_C);
    }
    
    private void layoutComponents() {
        setLayout(new BorderLayout(10, 10));
        
        // Title panel
        JPanel titlePanel = new JPanel(new BorderLayout(5, 5));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        titlePanel.add(new JLabel("Title:"), BorderLayout.WEST);
        titlePanel.add(titleField, BorderLayout.CENTER);
        
        // Content panel
        JPanel contentPanel = new JPanel(new BorderLayout(5, 5));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        contentPanel.add(new JLabel("Content:"), BorderLayout.NORTH);
        
        JScrollPane scrollPane = new JScrollPane(contentArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createLoweredBevelBorder());
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        
        // Add panels to dialog
        add(titlePanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void setupEventHandlers() {
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveNote();
            }
        });
        
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelDialog();
            }
        });
        
        // Handle window closing
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                cancelDialog();
            }
        });
        
        // Handle Enter key in title field
        titleField.