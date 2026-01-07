package chess7.pieces;

import chess7.logic.MoveValidator;

public class Queen extends Piece {
    public Queen(boolean isWhite) { super(isWhite, "Queen"); }
    
    @Override
    public String getSymbol() { return isWhite() ? "♕" : "♛"; }
    
    @Override
    public boolean isValidMove(int startX, int startY, int endX, int endY, Piece[][] board) {
        return (startX == endX || startY == endY || Math.abs(startX - endX) == Math.abs(startY - endY))
                && !MoveValidator.isPathBlocked(startX, startY, endX, endY, board);
    }
}