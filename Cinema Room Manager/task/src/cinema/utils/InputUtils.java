package cinema.utils;

import java.util.Scanner;

public class InputUtils {
    Scanner scanner;

    public InputUtils(Scanner scanner) {
        this.scanner = scanner;
    }

    public int readInt() {
        return scanner.nextInt();
    }
}