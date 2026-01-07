package chess7.pieces;

public class Pawn extends Piece {
    public Pawn(boolean isWhite) { super(isWhite, "Pawn"); }
    
    @Override
    public String getSymbol() { return isWhite() ? "♙" : "♟"; }
    
    @Override
    public boolean isValidMove(int startX, int startY, int endX, int endY, Piece[][] board) {
        int direction = isWhite() ? -1 : 1;
        
        // Gerak maju 1 langkah
        if (startY == endY && endX == startX + direction && board[endX][endY] == null) {
            return true;
        }
        // Gerak maju 2 langkah dari posisi awal
        if (startY == endY && ((isWhite() && startX == 6) || (!isWhite() && startX == 1))) {
            if (endX == startX + (2 * direction) && board[startX + direction][endY] == null && board[endX][endY] == null) {
                return true;
            }
        }
        // Makan lawan (diagonal)
        if (Math.abs(startY - endY) == 1 && endX == startX + direction && board[endX][endY] != null) {
            return true;
        }
        return false;
    }
}