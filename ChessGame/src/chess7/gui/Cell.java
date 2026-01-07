package chess7.gui;

import javax.swing.JButton;
import java.awt.Font;

public class Cell extends JButton {
    public int row, col;
    
    public Cell(int r, int c) {
        this.row = r;
        this.col = c;
        setFocusPainted(false);
        setBorderPainted(false); 
        setFont(new Font("Serif", Font.BOLD, 42));
    }
}