package chess7.pieces;

public class Knight extends Piece {
    public Knight(boolean isWhite) { super(isWhite, "Knight"); }
    
    @Override
    public String getSymbol() { return isWhite() ? "♘" : "♞"; }
    
    @Override
    public boolean isValidMove(int startX, int startY, int endX, int endY, Piece[][] board) {
        int dx = Math.abs(startX - endX);
        int dy = Math.abs(startY - endY);
        return dx * dy == 2;
    }
}