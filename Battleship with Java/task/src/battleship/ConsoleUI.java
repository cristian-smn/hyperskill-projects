package battleship;

import java.util.Scanner;

public class ConsoleUI {
    Scanner scanner;
    Player player1;
    Player player2;
    Player currentPlayer;

    public ConsoleUI(Scanner scanner, Player player1, Player player2) {
        this.scanner = scanner;
        this.player1 = player1;
        this.player2 = player2;
        this.currentPlayer = player1;
    }

    public void run() {
        boolean isFogOfWar = false;
        for (int i = 1; i <= 2; i++) {
            System.out.println("Player " + i + ", place your ships to the game field");
            char[][] layout = currentPlayer.getBoard().getLayout();

            drawBoard(currentPlayer.getBoard().getLayout(), isFogOfWar);
            int index = 0;
            while (index < ShipType.values().length) {
                ShipType shipType = ShipType.values()[index];
                System.out.println("Enter the coordinates of the " + shipType.getLabel()
                        + " (" + shipType.getLength() + " cells):");
                boolean isValid = false;
                while (!isValid) {
                    String point1 = scanner.next();
                    String point2 = scanner.next();
                    Ship ship = null;
                    try {
                        Coordinate coordinate1 = Coordinate.fromString(point1);
                        Coordinate coordinate2 = Coordinate.fromString(point2);
                        ship = new Ship(shipType, coordinate1, coordinate2);
                        if (!currentPlayer.getBoard().isAdjacent(ship)) {
                            currentPlayer.getBoard().addShip(ship);
                            index++;
                            drawBoard(layout, isFogOfWar);
                            isValid = true;
                        }
                    } catch (IllegalArgumentException iae) {
                        System.out.println(iae.getMessage());
                    }
                }
            }
            if (i == 1)
            {
                System.out.println("Press Enter and pass the move to another player\n" + "...");
                scanner.nextLine();
                scanner.nextLine();
                changePlayer();
            }
        }

        scanner.nextLine();

        boolean gameOver = false;
        while (!gameOver) {
            System.out.println("Press Enter and pass the move to another player\n" + "...");
            scanner.nextLine();
            changePlayer();
            draw();
            System.out.println(currentPlayer.getName() + ", it's your turn:");
            Coordinate shot = null;
            boolean isValid = false;
            while (!isValid) {
                String shotStr = scanner.nextLine();
                try {
                    shot = Coordinate.fromString(shotStr);
                    isValid = true;
                } catch (IllegalArgumentException iae) {
                    System.out.println(iae.getMessage());
                }
            }

            Player otherPlayer = (currentPlayer == player1) ? player2 : player1;
            Board.ShotStatus shotStatus = otherPlayer.getBoard().shoot(shot);
            switch (shotStatus) {
                case HIT -> System.out.println("You hit a ship!");
                case MISSED -> System.out.println("You missed!");
                case SUNK -> System.out.println("You sank a ship!");
            }
            gameOver = otherPlayer.getBoard().isGameOver();
        }

        System.out.println("You sank the last ship. You won. Congratulations!");
    }

    private void changePlayer() {
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
    }

    public void drawBoard(char[][] layout, boolean isFogOfWar) {
        System.out.print(" ");
        for (int i = 0; i < layout.length; i++) {
            System.out.print(" " + (i + 1));
        }
        System.out.println();

        for (int i = 0; i < layout.length; i++) {
            System.out.print((char)('A' + i));
            for (int j = 0; j < layout[i].length; j++) {
                if (isFogOfWar && layout[i][j] == 'O') {
                    System.out.print(" ~");
                } else {
                    System.out.print(" " + layout[i][j]);
                }
            }
            System.out.println();
        }
    }

    public void draw() {
        Player otherPlayer = (currentPlayer == player1) ? player2 : player1;
        drawBoard(otherPlayer.getBoard().getLayout(), true);
        System.out.println("---------------------");
        drawBoard(currentPlayer.getBoard().getLayout(), false);
    }
}
