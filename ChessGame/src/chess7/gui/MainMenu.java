package chess7.gui;

import javax.swing.*;
import java.awt.*;

public class MainMenu extends JFrame {
    private JPanel mainPanel;
    private JLabel title;
    private JLabel subtitle;

    public MainMenu() {
        setTitle("The Chess - By Hansel Kristanzen");
        setSize(450, 550); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        mainPanel.setBackground(new Color(245, 245, 245)); // Light background

        // Title Label
        title = new JLabel("THE CHESS");
        title.setFont(new Font("Segoe UI", Font.BOLD, 36));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setForeground(new Color(40, 40, 40));

        // Subtitle Label
        subtitle = new JLabel("By Hansel Kristanzen");
        subtitle.setFont(new Font("Segoe UI", Font.ITALIC, 16));
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        subtitle.setForeground(new Color(100, 100, 100));
        
        // Buttons
        JButton playUnlimitedBtn = createMenuButton("Play Unlimited Time");
        JButton playBlitzBtn = createMenuButton("Play Blitz Chess (10 Min)");
        JButton historyBtn = createMenuButton("Match History");
        JButton aboutBtn = createMenuButton("About");
        JButton quitBtn = createMenuButton("Quit");

        // --- ACTION LISTENERS ---
        playUnlimitedBtn.addActionListener(e -> startGame(false));
        playBlitzBtn.addActionListener(e -> startGame(true));

        historyBtn.addActionListener(e -> {
            this.setVisible(false);
            new HistoryViewer().setVisible(true);
        });
        
        aboutBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, 
                "A Chess Game\nMade by Hansel Kristanzen", 
                "About", 
                JOptionPane.INFORMATION_MESSAGE);
        });

        quitBtn.addActionListener(e -> System.exit(0));

        // Add Components
        mainPanel.add(title);
        mainPanel.add(subtitle);
        mainPanel.add(playUnlimitedBtn);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(playBlitzBtn);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(historyBtn);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(aboutBtn);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(quitBtn);

        add(mainPanel);
    }
    
    private JButton createMenuButton(String text) {
        JButton btn = new JButton(text);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btn.setMaximumSize(new Dimension(300, 40));
        btn.setFocusPainted(false);
        return btn;
    }

    private void startGame(boolean isBlitz) {
        try {
            String p1 = JOptionPane.showInputDialog(this, "Masukkan Nama Pemain 1 (Putih):");
            if (p1 == null) return; 
            validateInput(p1);
            
            String p2 = JOptionPane.showInputDialog(this, "Masukkan Nama Pemain 2 (Hitam):");
            if (p2 == null) return; 
            validateInput(p2);
            
            new ChessBoard(p1, p2, isBlitz).setVisible(true);
            this.setVisible(false); 
            
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Input Invalid", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            // Ignore other exceptions
        }
    }

    private void validateInput(String input) throws IllegalArgumentException {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException("Nama pemain tidak boleh kosong!");
        }
        if (input.length() > 15) {
            throw new IllegalArgumentException("Nama pemain maksimal 15 karakter!");
        }
    }
}