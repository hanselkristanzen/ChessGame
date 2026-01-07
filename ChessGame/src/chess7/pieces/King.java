package chess7.pieces;

public class King extends Piece {
    public King(boolean isWhite) { super(isWhite, "King"); }
    
    @Override
    public String getSymbol() { return isWhite() ? "♔" : "♚"; }
    
    @Override
    public boolean isValidMove(int startX, int startY, int endX, int endY, Piece[][] board) {
        int dx = Math.abs(startX - endX);
        int dy = Math.abs(startY - endY);
        return dx <= 1 && dy <= 1;
    }
}