package numbers;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            NumberService numberService = new NumberService();
            RequestValidator requestValidator = new RequestValidator();
            ConsoleUI consoleUI = new ConsoleUI(scanner, numberService, requestValidator);

            consoleUI.run();
        }
    }
}