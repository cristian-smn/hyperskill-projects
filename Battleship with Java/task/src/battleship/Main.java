package battleship;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        ConsoleUI consoleUI = new ConsoleUI(scanner, player1, player2);
        consoleUI.run();
    }
}
