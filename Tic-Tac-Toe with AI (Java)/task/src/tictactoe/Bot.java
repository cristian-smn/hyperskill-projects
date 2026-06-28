package tictactoe;

import java.util.ArrayList;
import java.util.List;

public abstract class Bot implements Player {
    protected List<Coordinates> getEmptyCells(char[][] board) {
        List<Coordinates> emptyCells = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == ' ') {
                    emptyCells.add(new Coordinates(i + 1, j + 1));
                }
            }
        }
        return emptyCells;
    }
}