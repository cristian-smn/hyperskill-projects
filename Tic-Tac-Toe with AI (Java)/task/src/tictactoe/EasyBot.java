package tictactoe;

import java.util.List;
import java.util.Random;

public class EasyBot extends Bot {
    @Override
    public Coordinates getMove(char[][] board, char mySymbol) {
        System.out.println("Making move level \"easy\"");
        Random random = new Random();
        List<Coordinates> emptyCells = getEmptyCells(board);

        return emptyCells.get(random.nextInt(emptyCells.size()));
    }
}
