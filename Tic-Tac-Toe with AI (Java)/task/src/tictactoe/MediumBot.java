package tictactoe;

import java.util.*;

import static tictactoe.Game.WINNING_LINES;

public class MediumBot extends Bot {
    @Override
    public Coordinates getMove(char[][] board, char mySymbol) {
        System.out.println("Making move level \"medium\"");
        char opponentSymbol = (mySymbol == 'X') ? 'O' : 'X';

        Optional<Coordinates> winningMove = findTacticalMove(board, mySymbol);
        if (winningMove.isPresent()) {
            return winningMove.get();
        }

        Optional<Coordinates> blockingMove = findTacticalMove(board, opponentSymbol);
        if (blockingMove.isPresent()) {
            return blockingMove.get();
        }

        return getRandomMove(board);
    }

    private Coordinates getRandomMove(char[][] board) {
        Random random = new Random();
        List<Coordinates> emptyCells = getEmptyCells(board);

        return emptyCells.get(random.nextInt(emptyCells.size()));
    }

    private Optional<Coordinates> findTacticalMove(char[][] board, char targetSymbol) {
        for (int[][] line : WINNING_LINES) {
            int targetCount = 0;
            int emptyCount = 0;
            Coordinates emptySpot = null;

            for (int[] cell : line) {
                char current = board[cell[0]][cell[1]];

                if (targetSymbol == current) {
                    targetCount++;
                } else if (current == ' ') {
                    emptyCount++;
                    emptySpot = new Coordinates(cell[0] + 1, cell[1] + 1);
                }
            }
            if (targetCount == 2 && emptyCount == 1) {
                return Optional.of(emptySpot);
            }
        }
        return Optional.empty();
    }
}