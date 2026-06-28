package tictactoe;

import tictactoe.Game.GameState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    private enum Command {
        START, EXIT
    }

    private enum Parameter {
        USER, EASY, MEDIUM, HARD
    }

    private static final int PARAMETERS_NUM = 2;

    record Request(Command command, ArrayList<Parameter> parameters) {}

    private static Player createPlayer(Parameter parameter, Scanner scanner) {
        return switch (parameter) {
            case USER -> new User(scanner);
            case EASY -> new EasyBot();
            case MEDIUM -> new MediumBot();
            case HARD -> new HardBot();
        };
    }

    private static Optional<Command> validateCommand(String commandStr, int paramNum) {
        return switch (commandStr) {
            case "start" -> (paramNum == 2) ? Optional.of(Command.START) : Optional.empty();
            case "exit" -> (paramNum == 0) ? Optional.of(Command.EXIT) : Optional.empty();
            default -> Optional.empty();
        };
    }

    private static Optional<ArrayList<Parameter>> getParams(String[] paramsStr) {
        ArrayList<Parameter> parameters = new ArrayList<>();
        for (int i = 0; i < PARAMETERS_NUM; i++) {
            Parameter parameter = switch (paramsStr[i]) {
                case "user" -> Parameter.USER;
                case "easy" -> Parameter.EASY;
                case "medium" -> Parameter.MEDIUM;
                default -> null;
            };
            if (parameter == null) {
                return Optional.empty();
            }
            parameters.add(parameter);
        }
        return Optional.of(parameters);
    }

    private static Optional<Request> validateRequest(String request) {
        String[] tokens = request.split(" ");
        String commandStr = tokens[0];

        Optional<Command> commandOpt = validateCommand(commandStr, tokens.length - 1);
        if (commandOpt.isEmpty()){
            return Optional.empty();
        }
        if (commandOpt.get() == Command.EXIT) {
            return Optional.of(new Request(Command.EXIT, null));
        }

        Optional<ArrayList<Parameter>> paramsOpt = getParams(Arrays.copyOfRange(tokens, 1, tokens.length));
        if (paramsOpt.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(new Request(Command.START, paramsOpt.get()));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Optional<Request> requestOpt;
        while (true) {
            System.out.println("Input command:");
            String command = scanner.nextLine();

            requestOpt = validateRequest(command);
            if (requestOpt.isEmpty()) {
                System.out.println("Bad parameters!");
                continue;
            }
            if (requestOpt.get().command() == Command.EXIT) {
                return;
            }
            break;
        }

        Game game = new Game();
        Player playerX = createPlayer(requestOpt.get().parameters().getFirst(), scanner);
        Player playerO = createPlayer(requestOpt.get().parameters().getLast(), scanner);

        draw(game.getBoard());
        do {
            Player currentPlayer = (game.getCurrentPlayer() == 'X') ? playerX : playerO;
            Coordinates coords = currentPlayer.getMove(game.getBoard(), game.getCurrentPlayer());

            if (game.validateCoordinates(coords)) {
                game.move(coords);
                draw(game.getBoard());
            }
        } while (game.getGameState() == GameState.GAME_NOT_FINISHED);

        drawGameState(game.getGameState());
        scanner.close();
    }

    private static void drawGameState(GameState gameState) {
        switch (gameState) {
            case DRAW -> System.out.println("Draw");
            case X_WINS -> System.out.println("X wins");
            case Y_WINS -> System.out.println("O wins");
            case GAME_NOT_FINISHED -> System.out.println("Game not finished");
        }
    }

    private static void draw(char[][] board) {
        System.out.println("---------");
        for (int i = 0; i < board.length; i++) {
            System.out.print("| ");
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j]);
                if (j != board[i].length - 1) {
                    System.out.print(' ');
                }
            }
            System.out.print(" |");
            System.out.println();
        }
        System.out.println("---------");
    }
}
