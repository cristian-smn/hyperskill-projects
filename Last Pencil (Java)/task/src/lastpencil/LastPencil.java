package lastpencil;

import java.util.Random;

public class LastPencil {
    public enum GameState {
        NOT_FINISHED(""),
        JOHN_WON("John won!"),
        JACK_WON("Jack won!");

        private final String value;
        GameState(String value) {
            this.value = value;
        }
        public String getValue() {
            return value;
        }
    }
    private final static String PLAYER1 = "John";
    private final static String PLAYER2 = "Jack";

    private String player;
    private int pencils;

    public String getPlayer() {
        return player;
    }

    public int getPencils() {
        return pencils;
    }

    public void move(int pencilsReduced) {
        pencils -= pencilsReduced;
        changePlayer();
    }

    public GameState getGameState() {
        if (pencils <= 0) {
            return (PLAYER1.equals(player)) ? GameState.JOHN_WON : GameState.JACK_WON;
        }
        return GameState.NOT_FINISHED;
    }

    public boolean isBot() {
        return PLAYER2.equals(player);
    }

    public void initialize(int pencils, String player) {
        this.pencils = pencils;
        this.player = player;
    }

    public int getBotPencils() {
        if (pencils == 1) {
            return 1;
        }
        Random random = new Random();
        return switch (pencils % 4) {
            case 0 -> 3;
            case 1 -> random.nextInt(3) + 1;
            case 2 -> 1;
            case 3 -> 2;
            default -> throw new IllegalStateException("Unexpected pencils % 4 value");
        };
    }

    private void changePlayer() {
        player = (PLAYER1.equals(player)) ? PLAYER2 : PLAYER1;
    }

    public int validateAndParsePencils(String pencilsReducedStr) {
        switch (pencilsReducedStr) {
            case "1":
            case "2":
            case "3":
                int pencilsReduced = Integer.parseInt(pencilsReducedStr);
                if (pencilsReduced > pencils) {
                    throw new PencilsNumberOutOfBounds("Too many pencils were taken");
                }
                return pencilsReduced;
            default:
                throw new PencilsNumberOutOfBounds("Possible values: '1', '2' or '3'");
        }
    }

    public void validateInitialPencils(int pencilsReduced) {
        if (pencilsReduced == 0) {
            throw new PencilsNotPositive("The number of pencils should be positive");
        }
    }

    public void validatePlayer(String player) {
        if (!PLAYER1.equals(player) && !PLAYER2.equals(player)) {
            throw new IllegalPlayerNameException("Choose between 'John' and 'Jack'");
        }
    }
}
