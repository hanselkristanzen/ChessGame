package chess7.pieces;

public abstract class Piece {
    private boolean isWhite;
    private String name;

    public Piece(boolean isWhite, String name) {
        this.isWhite = isWhite;
        this.name = name;
    }

    public boolean isWhite() { return isWhite; }
    public String getName() { return name; }

    public abstract boolean isValidMove(int startX, int startY, int endX, int endY, Piece[][] board);
    public abstract String getSymbol();
}