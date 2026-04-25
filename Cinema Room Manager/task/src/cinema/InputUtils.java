package cinema;

import java.util.Scanner;

public final class InputUtils {
    private InputUtils(){}

    public static int readInt(Scanner scanner, String prompt) {
        System.out.println(prompt);
        return scanner.nextInt();
    }
}
