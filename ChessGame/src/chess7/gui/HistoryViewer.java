package chess7.gui;

import chess7.utils.FileManager;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class HistoryViewer extends JFrame {
    public HistoryViewer() {
        setTitle("The Chess - Match History");
        setSize(800, 500);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        
        // Fixed Light Theme Colors
        Color bgColor = new Color(245, 245, 245);
        Color fgColor = Color.BLACK;
        Color tableBg = Color.WHITE;
        Color headerBg = new Color(230, 230, 230);
        Color headerFg = Color.BLACK;

        DefaultTableModel model = FileManager.readHistory();
        JTable table = new JTable(model);
        
        table.setFillsViewportHeight(true);
        table.setRowHeight(25);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.setBackground(tableBg);
        table.setForeground(fgColor);
        
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.getTableHeader().setBackground(headerBg);
        table.getTableHeader().setForeground(headerFg);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        scrollPane.getViewport().setBackground(bgColor);
        add(scrollPane, BorderLayout.CENTER);

        JButton closeBtn = new JButton("Tutup");
        closeBtn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        closeBtn.addActionListener(e -> {
            this.dispose();
            new MainMenu().setVisible(true);
        });
        
        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(bgColor);
        btnPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        btnPanel.add(closeBtn);
        add(btnPanel, BorderLayout.SOUTH);
    }
}