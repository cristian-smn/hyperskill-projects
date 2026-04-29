package numbers;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            AmazingNumbers amazingNumbers = new AmazingNumbers();
            RequestValidator requestValidator = new RequestValidator();
            ConsoleUI consoleUI = new ConsoleUI(scanner, amazingNumbers, requestValidator);
            consoleUI.run();
        }
    }
}