package chess7.pieces;

import chess7.logic.MoveValidator;

public class Rook extends Piece {
    public Rook(boolean isWhite) { super(isWhite, "Rook"); }
    
    @Override
    public String getSymbol() { return isWhite() ? "♖" : "♜"; }
    
    @Override
    public boolean isValidMove(int startX, int startY, int endX, int endY, Piece[][] board) {
        if (startX != endX && startY != endY) return false;
        return !MoveValidator.isPathBlocked(startX, startY, endX, endY, board);
    }
}