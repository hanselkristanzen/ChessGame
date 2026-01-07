package chess7.pieces;

import chess7.logic.MoveValidator;

public class Bishop extends Piece {
    public Bishop(boolean isWhite) { super(isWhite, "Bishop"); }
    
    @Override
    public String getSymbol() { return isWhite() ? "♗" : "♝"; }
    
    @Override
    public boolean isValidMove(int startX, int startY, int endX, int endY, Piece[][] board) {
        if (Math.abs(startX - endX) != Math.abs(startY - endY)) return false;
        return !MoveValidator.isPathBlocked(startX, startY, endX, endY, board);
    }
}