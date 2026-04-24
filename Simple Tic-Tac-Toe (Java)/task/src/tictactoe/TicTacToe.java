package tictactoe;

import java.util.Scanner;

public class TicTacToe {
    private static final int MAP_DIM = 3;
    private final char[][] map = new char[MAP_DIM][MAP_DIM];
    private char player = 'X';
    private record Move (int row, int col){}

    private enum GameState {
        GAME_NOT_FINISHED("Game not finished"),
        DRAW("Draw"),
        X_WINS("X wins"),
        O_WINS("O wins");

        private final String value;
        GameState(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);
        initialize();
        draw();

        GameState gameState = verifyGameState();
        while (gameState.equals(GameState.GAME_NOT_FINISHED)) {
            Move move = getUserInput(scanner);
            int row = move.row();
            int col = move.col();

            if (isOccupied(row, col)) {
                System.out.println("This cell is occupied! Choose another one!");
                continue;
            }
            map[row - 1][col - 1] = player;
            changePlayer();
            draw();
            gameState = verifyGameState();
        }
        System.out.println(gameState.getValue());
        scanner.close();
    }

    private Move getUserInput(Scanner scanner) {
        while (true) {
            try {
                int row = Integer.parseInt(scanner.next());
                int col = Integer.parseInt(scanner.next());
                if ((row < 1 || row > MAP_DIM) || (col < 1 || col > MAP_DIM)) {
                    System.out.println("Coordinates should be from 1 to 3!");
                    continue;
                }
                return new Move(row, col);
            } catch (NumberFormatException nfe) {
                scanner.nextLine(); //consumes \n
                System.out.println("You should enter numbers!");
            }
        }
    }

    private void changePlayer() {
        player = (player == 'X') ? 'O' : 'X';
    }

    private boolean isOccupied(int row, int col) {
        return map[row - 1][col - 1] != ' ';
    }

    private void initialize() {
        for (int i = 0; i < MAP_DIM; i++) {
            for (int j = 0; j < MAP_DIM; j++) {
                map[i][j] = ' ';
            }
        }
    }

    private void draw() {
        System.out.println("---------");
        for (int i = 0; i < MAP_DIM; i++) {
            System.out.print("| ");
            for (int j = 0; j < MAP_DIM; j++) {
                System.out.print(map[i][j]);
                if (j < MAP_DIM - 1) {
                    System.out.print(' ');
                }
            }
            System.out.print(" |");
            System.out.println();
        }
        System.out.println("---------");
    }

    private GameState getWinner(char player) {
        return (player == 'X') ? GameState.X_WINS : GameState.O_WINS;
    }

    private GameState verifyGameState() {
        if (map[0][0] == map[1][1] && map[1][1] == map[2][2] && map[1][1] != ' ')
            return getWinner(map[1][1]);
        if (map[0][2] == map[1][1] && map[1][1] == map[2][0] && map[1][1] != ' ')
            return getWinner(map[1][1]);

        for (int i = 0; i < MAP_DIM; i++) {
            if (map[i][0] == map[i][1] && map[i][1] == map[i][2] && map[i][1] != ' ')
                return getWinner(map[i][1]);

            if (map[0][i] == map[1][i] && map[1][i] == map[2][i] && map[1][i] != ' ')
                return getWinner(map[1][i]);
        }

        for (int i = 0; i < MAP_DIM; i++) {
            for (int j = 0; j < MAP_DIM; j++) {
                if (map[i][j] == ' ') return GameState.GAME_NOT_FINISHED;
            }
        }

        return GameState.DRAW;
    }
}
