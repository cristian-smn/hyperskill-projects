package cinema;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        //Main are putin I/O dar un CinemaInitializer care sa creeze obiectul Cinema era prea mult
        // si nu neaparat cea mai buna decizie
        int rows = InputUtils.readInt(scanner, "Enter the number of rows:");
        int seats = InputUtils.readInt(scanner, "Enter the number of seats in each row:");
        Cinema cinema = new Cinema(rows, seats);
        ConsoleUI consoleUI = new ConsoleUI(cinema, scanner);
        consoleUI.run();

        scanner.close();
    }
}