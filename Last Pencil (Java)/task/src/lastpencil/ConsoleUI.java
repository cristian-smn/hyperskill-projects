package lastpencil;

import java.util.Scanner;

public class ConsoleUI {
    private final LastPencil lastPencil;

    public ConsoleUI(LastPencil lastPencil) {
        this.lastPencil = lastPencil;
    }

    private int getUserPencils(Scanner scanner) {
        while (true) {
            String pencils = scanner.nextLine();
            try {
                return lastPencil.validateAndParsePencils(pencils);
            } catch (InvalidPencilsException ipe) {
                System.out.println(ipe.getMessage());
            }
        }
    }

    private int getInitialPencils(Scanner scanner) {
        while(true) {
            try {
                int pencils = Integer.parseInt(scanner.nextLine());
                if (pencils < 0) {
                    System.out.println("The number of pencils should be numeric");
                    continue;
                }
                try {
                    lastPencil.validateInitialPencils(pencils);
                    return pencils;
                } catch (InvalidPencilsException ipe) {
                    System.out.println(ipe.getMessage());
                }
            } catch (NumberFormatException nfe) {
                System.out.println("The number of pencils should be numeric");
            }
        }
    }

    private String getPlayer(Scanner scanner) {
        while (true) {
            String player = scanner.nextLine();
            try {
                lastPencil.validatePlayer(player);
                return player;
            } catch (IllegalPlayerNameException ipn) {
                System.out.println(ipn.getMessage());
            }
        }
    }

    private void draw() {
        for (int i = 0; i < lastPencil.getPencils(); i++) {
            System.out.print('|');
        }
        System.out.println();
    }

    private void initializeGame(Scanner scanner) {
        System.out.println("How many pencils would you like to use:");
        int pencils = getInitialPencils(scanner);

        System.out.println("Who will be the first (John, Jack):");
        String player = getPlayer(scanner);

        lastPencil.initialize(pencils, player);
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        initializeGame(scanner);

        LastPencil.GameState gameState = lastPencil.getGameState();
        while (gameState == LastPencil.GameState.NOT_FINISHED) {
            draw();
            String currentPlayer = lastPencil.getPlayer();

            System.out.println(currentPlayer + "'s turn" + ((lastPencil.isBot()) ? ":" : "!"));

            int pencilsReduced;
            if (lastPencil.isBot()) {
                pencilsReduced = lastPencil.getBotPencils();
                System.out.println(pencilsReduced);
            } else {
                pencilsReduced = getUserPencils(scanner);
            }

            lastPencil.move(pencilsReduced);
            gameState = lastPencil.getGameState();
        }
        System.out.println(gameState.getValue());
        scanner.close();
    }
}
