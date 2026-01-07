package chess7.gui;

import chess7.pieces.*;
import chess7.utils.FileManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ChessBoard extends JFrame {
    private Cell[][] cells = new Cell[8][8];
    private Piece[][] pieces = new Piece[8][8];
    private boolean isWhiteTurn = true;
    private Cell selectedCell = null;
    private JLabel statusLabel;
    
    // Data Game
    private ArrayList<String> moveHistoryList;
    private ArrayList<String> capturedPiecesList;
    private String playerWhiteName;
    private String playerBlackName;

    // Timer Blitz
    private boolean isBlitzMode;
    private int whiteTimeSeconds;
    private int blackTimeSeconds;
    private Timer gameTimer;
    private JLabel timerLabelWhite;
    private JLabel timerLabelBlack;
    
    // Theme Constants
    private final Color colorBoardLight = new Color(240, 217, 181); 
    private final Color colorBoardDark = new Color(181, 136, 99);   
    private final Color colorHighlight = new Color(255, 255, 100); 
    private final Color colorPanelBg = new Color(50, 50, 60); 
    private final Color colorText = new Color(30, 30, 30); 
    private final Color colorMainBg = new Color(40, 40, 45);

    public ChessBoard(String p1, String p2, boolean blitzMode) {
        this.playerWhiteName = p1;
        this.playerBlackName = p2;
        this.isBlitzMode = blitzMode;
        
        if (isBlitzMode) {
            this.whiteTimeSeconds = 600;
            this.blackTimeSeconds = 600;
        }

        moveHistoryList = new ArrayList<>();
        capturedPiecesList = new ArrayList<>();

        String modeTitle = isBlitzMode ? " [BLITZ MODE]" : " [UNLIMITED]";
        setTitle("The Chess: " + p1 + " vs " + p2 + modeTitle);
        setSize(1000, 950);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // --- Header Panel (Timer & Info) ---
        JPanel topPanel = new JPanel(new GridLayout(1, 3));
        topPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        topPanel.setBackground(colorPanelBg);

        timerLabelWhite = createStyledLabel("White: " + formatTime(whiteTimeSeconds));
        JLabel vsLabel = createStyledLabel("VS");
        timerLabelBlack = createStyledLabel("Black: " + formatTime(blackTimeSeconds));

        if (!isBlitzMode) {
            timerLabelWhite.setText("White: ∞");
            timerLabelBlack.setText("Black: ∞");
        }

        topPanel.add(timerLabelWhite);
        topPanel.add(vsLabel);
        topPanel.add(timerLabelBlack);
        add(topPanel, BorderLayout.NORTH);

        // --- Board Panel ---
        JPanel boardContainer = new JPanel(new GridBagLayout()); 
        boardContainer.setBackground(colorMainBg);
        
        JPanel boardPanel = new JPanel(new GridLayout(8, 8));
        boardPanel.setPreferredSize(new Dimension(750, 750)); 
        boardPanel.setBorder(BorderFactory.createLineBorder(new Color(100, 70, 50), 5)); 
        
        initializeBoard(boardPanel);
        initializePieces();
        
        boardContainer.add(boardPanel);
        add(boardContainer, BorderLayout.CENTER);
        
        // --- Bottom Status & Buttons ---
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY));
        
        statusLabel = new JLabel("Giliran: " + playerWhiteName + " (PUTIH)", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        statusLabel.setForeground(colorText);
        statusLabel.setBorder(BorderFactory.createEmptyBorder(15,10,15,10));
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(Color.WHITE);

        JButton historyButton = createStyledButton("Lihat History", new Color(70, 130, 180));
        JButton resetButton = createStyledButton("Reset Game", new Color(255, 165, 0));
        JButton surrenderButton = createStyledButton("Surrender", new Color(220, 53, 69)); 

        historyButton.addActionListener(e -> showHistoryDialog());
        resetButton.addActionListener(e -> resetGame());
        surrenderButton.addActionListener(e -> surrenderGame());

        buttonPanel.add(historyButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(surrenderButton);
        
        bottomPanel.add(statusLabel, BorderLayout.NORTH);
        bottomPanel.add(buttonPanel, BorderLayout.CENTER);

        add(bottomPanel, BorderLayout.SOUTH);
        
        FileManager.initFile();

        if (isBlitzMode) {
            setupTimer();
        }
    }
    
    // --- Helper Methods ---

    private JLabel createStyledLabel(String text) {
        JLabel lbl = new JLabel(text, SwingConstants.CENTER);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lbl.setForeground(new Color(240, 240, 240)); 
        return lbl;
    }

    private JButton createStyledButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(new EmptyBorder(10, 20, 10, 20));
        return btn;
    }

    private void surrenderGame() {
        String currentPlayer = isWhiteTurn ? playerWhiteName : playerBlackName;
        String winner = isWhiteTurn ? playerBlackName : playerWhiteName; 
        
        int response = JOptionPane.showConfirmDialog(this, 
            currentPlayer + ", apakah Anda yakin ingin menyerah?", 
            "Konfirmasi Surrender", 
            JOptionPane.YES_NO_OPTION);
            
        if (response == JOptionPane.YES_OPTION) {
            if (gameTimer != null) gameTimer.stop();
            
            FileManager.saveToCSV(
                currentPlayer, 
                "KING", 
                "RESIGN", 
                "SURRENDER", 
                winner + " WINS BY RESIGNATION"
            );
            
            JOptionPane.showMessageDialog(this, 
                currentPlayer + " menyerah!\nPemenangnya adalah " + winner + "!", 
                "Game Over", 
                JOptionPane.INFORMATION_MESSAGE);
            
            this.dispose();
            new MainMenu().setVisible(true);
        }
    }

    private void setupTimer() {
        gameTimer = new Timer(1000, e -> {
            if (isWhiteTurn) {
                whiteTimeSeconds--;
                timerLabelWhite.setText("White: " + formatTime(whiteTimeSeconds));
                if (whiteTimeSeconds <= 0) {
                    gameOver(playerBlackName + " (Hitam) Menang Waktu!");
                }
            } else {
                blackTimeSeconds--;
                timerLabelBlack.setText("Black: " + formatTime(blackTimeSeconds));
                if (blackTimeSeconds <= 0) {
                    gameOver(playerWhiteName + " (Putih) Menang Waktu!");
                }
            }
        });
        gameTimer.start();
    }

    private void gameOver(String message) {
        if (gameTimer != null) gameTimer.stop();
        JOptionPane.showMessageDialog(this, message);
        
        this.dispose();
        new MainMenu().setVisible(true);
    }

    private String formatTime(int totalSeconds) {
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    private void showHistoryDialog() {
        StringBuilder sb = new StringBuilder();
        sb.append("Riwayat Langkah (Sesi Ini):\n");
        sb.append("---------------------------------\n");
        
        if (moveHistoryList.isEmpty()) {
            sb.append("Belum ada langkah.");
        } else {
            for (int i = 0; i < moveHistoryList.size(); i++) {
                sb.append((i+1) + ". " + moveHistoryList.get(i) + "\n");
            }
        }
        
        sb.append("\nBidak yang dimakan:\n");
        sb.append(capturedPiecesList.toString());

        JOptionPane.showMessageDialog(this, new JTextArea(sb.toString()), "Game History", JOptionPane.INFORMATION_MESSAGE);
    }

    private void initializeBoard(JPanel panel) {
        boolean white = true;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                cells[i][j] = new Cell(i, j);
                cells[i][j].setBackground(white ? colorBoardLight : colorBoardDark);
                cells[i][j].addActionListener(new CellClickListener());
                panel.add(cells[i][j]);
                white = !white;
            }
            white = !white;
        }
    }

    private void initializePieces() {
        // Setup Hitam
        pieces[0][0] = new Rook(false); pieces[0][7] = new Rook(false);
        pieces[0][1] = new Knight(false); pieces[0][6] = new Knight(false);
        pieces[0][2] = new Bishop(false); pieces[0][5] = new Bishop(false);
        pieces[0][3] = new Queen(false); pieces[0][4] = new King(false);
        for(int i=0; i<8; i++) pieces[1][i] = new Pawn(false);

        // Setup Putih
        pieces[7][0] = new Rook(true); pieces[7][7] = new Rook(true);
        pieces[7][1] = new Knight(true); pieces[7][6] = new Knight(true);
        pieces[7][2] = new Bishop(true); pieces[7][5] = new Bishop(true);
        pieces[7][3] = new Queen(true); pieces[7][4] = new King(true);
        for(int i=0; i<8; i++) pieces[6][i] = new Pawn(true);

        renderBoard();
    }

    private void renderBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (pieces[i][j] != null) {
                    cells[i][j].setText(pieces[i][j].getSymbol());
                    if (pieces[i][j].isWhite()) {
                       cells[i][j].setForeground(Color.DARK_GRAY); 
                       cells[i][j].setText("<html><font color='white' style='text-shadow: 1px 1px 2px black;'>" + pieces[i][j].getSymbol() + "</font></html>");
                    } else {
                        cells[i][j].setForeground(Color.BLACK);
                    }
                } else {
                    cells[i][j].setText("");
                }
            }
        }
    }
    
    private void resetGame() {
        if (gameTimer != null) gameTimer.stop();
        pieces = new Piece[8][8];
        moveHistoryList.clear();
        capturedPiecesList.clear();
        
        if (isBlitzMode) {
            whiteTimeSeconds = 600;
            blackTimeSeconds = 600;
            timerLabelWhite.setText("White: 10:00");
            timerLabelBlack.setText("Black: 10:00");
            gameTimer.restart();
        }

        initializePieces();
        isWhiteTurn = true;
        selectedCell = null;
        statusLabel.setText("Giliran: " + playerWhiteName + " (PUTIH)");
        JOptionPane.showMessageDialog(this, "Permainan direset.");
    }
    
    private String convertCoord(int r, int c) {
        char colChar = (char) ('A' + c);
        int rowNum = 8 - r;
        return "" + colChar + rowNum;
    }

    // --- Inner Class: CellClickListener ---
    private class CellClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Cell clickedCell = (Cell) e.getSource();
            int r = clickedCell.row;
            int c = clickedCell.col;

            if (selectedCell == null) {
                if (pieces[r][c] != null) {
                    if (pieces[r][c].isWhite() != isWhiteTurn) {
                        JOptionPane.showMessageDialog(null, "Bukan giliranmu! Tunggu lawan.");
                        return;
                    }
                    selectedCell = clickedCell;
                    clickedCell.setBackground(colorHighlight);
                }
            } else {
                int startR = selectedCell.row;
                int startC = selectedCell.col;
                
                boolean originalColorWhite = (startR + startC) % 2 == 0;
                selectedCell.setBackground(originalColorWhite ? colorBoardLight : colorBoardDark);

                if (selectedCell != clickedCell) {
                    Piece pieceToMove = pieces[startR][startC];
                    
                    if (pieceToMove.isValidMove(startR, startC, r, c, pieces)) {
                        if (pieces[r][c] != null && pieces[r][c].isWhite() == pieceToMove.isWhite()) {
                            // Invalid move (makan teman)
                        } else {
                            boolean isCapture = pieces[r][c] != null;
                            String targetName = isCapture ? pieces[r][c].getName() : "-";
                            String note = isCapture ? "Memakan " + targetName : "Gerak Normal";
                            
                            if(isCapture) {
                                capturedPiecesList.add(targetName + "(" + (isWhiteTurn ? "Hitam" : "Putih") + ")");
                                if (pieces[r][c] instanceof King) {
                                    FileManager.saveToCSV(isWhiteTurn ? playerWhiteName : playerBlackName, pieceToMove.getName(), 
                                        convertCoord(startR, startC), convertCoord(r, c), "CHECKMATE/WIN");
                                    gameOver((isWhiteTurn ? playerWhiteName : playerBlackName) + " MENANG!");
                                    return;
                                }
                            }

                            pieces[r][c] = pieceToMove;
                            pieces[startR][startC] = null;
                            
                            String moveStr = String.format("[%s] %s: %s -> %s (%s)", 
                                    isWhiteTurn ? "Putih" : "Hitam", 
                                    pieceToMove.getName(), 
                                    convertCoord(startR, startC), 
                                    convertCoord(r, c),
                                    note);
                            moveHistoryList.add(moveStr);

                            FileManager.saveToCSV(
                                isWhiteTurn ? playerWhiteName : playerBlackName,
                                pieceToMove.getName(),
                                convertCoord(startR, startC),
                                convertCoord(r, c),
                                note
                            );

                            isWhiteTurn = !isWhiteTurn;
                            statusLabel.setText("Giliran: " + (isWhiteTurn ? playerWhiteName + " (PUTIH)" : playerBlackName + " (HITAM)"));
                        }
                    }
                }
                selectedCell = null;
                renderBoard();
            }
        }
    }
}
