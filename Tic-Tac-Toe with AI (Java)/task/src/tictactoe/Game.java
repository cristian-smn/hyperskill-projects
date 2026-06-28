package tictactoe;

import java.util.Arrays;

public class Game {
    public static final int[][][] WINNING_LINES = {
            {{0, 0}, {0, 1}, {0, 2}},
            {{1, 0}, {1, 1}, {1, 2}},
            {{2, 0}, {2, 1}, {2, 2}},

            {{0, 0}, {1, 0}, {2, 0}},
            {{0, 1}, {1, 1}, {2, 1}},
            {{0, 2}, {1, 2}, {2, 2}},

            {{0, 0}, {1, 1}, {2, 2}},
            {{0, 2}, {1, 1}, {2, 0}}
    };

    public enum GameState {
        X_WINS, Y_WINS, DRAW, GAME_NOT_FINISHED
    }

    private static final int BOARD_DIM = 3;

    private final char[][] board;
    private char currentPlayer;
    private int movesNum;
    //gamestate

    public char[][] getBoard() {
        return board;
    }

    public char getCurrentPlayer() {
        return currentPlayer;
    }

    public Game() {
        currentPlayer = 'X';
        movesNum = 0;
        board = new char[BOARD_DIM][BOARD_DIM];

        for (int i = 0; i < BOARD_DIM; i++) {
            Arrays.fill(board[i], ' ');
        }
    }

    public void move(Coordinates coords) {
        board[coords.x() - 1][coords.y() - 1] = currentPlayer;
        movesNum++;
        changePlayer();
    }

    public GameState getGameState() {
        for (int[][] line : WINNING_LINES) {
            char p1 = board[line[0][0]][line[0][1]];
            char p2 = board[line[1][0]][line[1][1]];
            char p3 = board[line[2][0]][line[2][1]];

            if (p1 != ' ' && p1 == p2 && p2 == p3) {
                return (p1 == 'X') ? GameState.X_WINS : GameState.Y_WINS;
            }
        }

        if (movesNum == 9) {
            return GameState.DRAW;
        }

        return GameState.GAME_NOT_FINISHED;
    }

    public boolean validateCoordinates(Coordinates coordinates) {
        if (coordinates.x() < 1 || coordinates.x() > BOARD_DIM || coordinates.y() < 1 || coordinates.y() > BOARD_DIM) {
            System.out.println("Coordinates should be from 1 to 3!");
            return false;
        }

        if (board[coordinates.x() - 1][coordinates.y() - 1] != ' ') {
            System.out.println("This cell is occupied! Choose another one!");
            return false;
        }

        return true;
    }

    public void changePlayer() {
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
    }
}