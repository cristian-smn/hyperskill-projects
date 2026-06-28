package tictactoe;

public interface Player {
    public Coordinates getMove(char[][] board, char mySymbol);
}
