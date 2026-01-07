package chess7.logic;

import chess7.pieces.Piece;

public class MoveValidator {
    public static boolean isPathBlocked(int x1, int y1, int x2, int y2, Piece[][] board) {
        int dx = Integer.compare(x2, x1);
        int dy = Integer.compare(y2, y1);
        int currX = x1 + dx;
        int currY = y1 + dy;
        
        while (currX != x2 || currY != y2) {
            if (board[currX][currY] != null) return true;
            currX += dx;
            currY += dy;
        }
        return false;
    }
}
