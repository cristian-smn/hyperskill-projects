package tictactoe;

import java.util.Scanner;

public class User implements Player {
    Scanner scanner;

    @Override
    public Coordinates getMove(char[][] board, char mySymbol) {
        System.out.println("Enter the coordinates:");
        String userInput = scanner.nextLine();

        String[] coordsStr = userInput.split(" ");
        int x, y;
        try {
            x = Integer.parseInt(coordsStr[0]);
            y = Integer.parseInt(coordsStr[1]);
        } catch (NumberFormatException nfe) {
            System.out.println("You should enter numbers!");
            return null;
        }

        if (coordsStr.length != 2) {
            System.out.println("You should have just 2 coordinates!");
            return null;
        }

        return new Coordinates(x, y);
    }

    public User(Scanner scanner) {
        this.scanner = scanner;
    }
}
