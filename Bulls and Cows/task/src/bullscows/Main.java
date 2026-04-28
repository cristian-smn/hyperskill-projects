package bullscows;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {
    private static final int NUMBER_OF_DIGITS = 10;
    private static final int NUMBER_OF_SYMBOLS = 36;
    private static final int UNICODE_0 = 48;
    private static final int UNICODE_a = 97;
    private record Grade(int bulls, int cows){}

    private static String getPseudoRandomNumber(int codeLength, int maxSymbols) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        ArrayList<Character> symbols = new ArrayList<>(maxSymbols);

        int limit = Math.min(NUMBER_OF_DIGITS, maxSymbols);
        for (int i = 0; i < limit; i++) {
            symbols.add((char) (UNICODE_0 + i));
        }
        for (int i = 0; i < maxSymbols - limit; i++) {
            symbols.add((char)(UNICODE_a + i));
        }

        while (sb.length() < codeLength) {
            int randomIndex = random.nextInt(symbols.size());
            sb.append(symbols.get(randomIndex));
            symbols.remove(randomIndex);
        }
        return sb.toString();
    }

    private static int getMaxSymbols (Scanner scanner) {
        int maxSymbols;
        if (scanner.hasNextInt()) {
            maxSymbols = scanner.nextInt();
            if (maxSymbols > NUMBER_OF_SYMBOLS) {
                System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
                return 0;
            }
            return maxSymbols;
        } else {
            System.out.println("Error: \"" + (scanner.nextLine()) + "\" isn't a valid number.");
            return 0;
        }
    }

    private static int getCodeLength(Scanner scanner) {
        int codeLength;
        if (scanner.hasNextInt()) {
            codeLength = scanner.nextInt();
            if (codeLength < 1) {
                System.out.println("Error: \"" + codeLength + "\" isn't a valid number.");
                return 0;
            }
            return codeLength;
        } else {
            System.out.println("Error: \"" + (scanner.nextLine()) + "\" isn't a valid number.");
            return 0;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input the length of the secret code:");
        int codeLength = getCodeLength(scanner);
        if (codeLength == 0) {
            return;
        }
        System.out.println("Input the number of possible symbols in the code:");
        int maxSymbols = getMaxSymbols(scanner);
        if (maxSymbols == 0) {
            return;
        }

        if (codeLength > maxSymbols) {
            System.out.println("Error: it's not possible to generate a code with a length of "
                    + codeLength + " with " + maxSymbols + " unique symbols.");
            return;
        }

        scanner.nextLine();
        String secretCode = getPseudoRandomNumber(codeLength, maxSymbols);
        getInfo(codeLength, maxSymbols);
        System.out.println("Okay, let's start a game!");

        int turns = 1;
        String input;
        do {
            System.out.println("Turn " + turns + ":");
            input = scanner.nextLine();
            Grade bullsAndCows = calculateBullsAndCows(input, secretCode);
            getGrade(bullsAndCows);
            turns++;
        } while (!secretCode.equals(input));

        System.out.println("Congratulations! You guessed the secret code.");
    }

    private static Grade calculateBullsAndCows(String input, String secretCode) {
        int bulls = 0, cows = 0;
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == secretCode.charAt(i)) {
                bulls++;
                continue;
            }
            for (int j = 0; j < secretCode.length(); j++) {
                if (secretCode.charAt(j) == input.charAt(i)) {
                    cows++;
                }
            }
        }
        return new Grade(bulls, cows);
    }

    private static void getGrade(Grade bullsAndCows) {
        int bulls = bullsAndCows.bulls();
        int cows = bullsAndCows.cows();

        System.out.print("Grade: ");
        if (bulls == 0 && cows == 0) {
            System.out.println("None");
            return;
        }

        String bullsStr = "";
        if (bulls != 0) {
            bullsStr += (bulls + " bull");
            if (bulls > 1) {
                bullsStr += "s";
            }
        }

        String cowsStr = "";
        if (cows != 0) {
            cowsStr += (cows + " cow");
            if (cows > 1) {
                cowsStr += "s";
            }
        }

        String andStr = "";
        if (bulls > 0 && cows > 0) {
            andStr = " and ";
        }
        System.out.println(bullsStr + andStr + cowsStr);
    }

    private static void getInfo(int codeLength, int maxSymbols) {
        System.out.print("The secret is prepared: ");

        for (int i = 0; i < codeLength; i++) {
            System.out.print("*");
        }
        System.out.print(" ");

        StringBuilder sb = new StringBuilder();

        sb.append("(");
        int lastDigit = Math.min(maxSymbols, NUMBER_OF_DIGITS) - 1;
        sb.append("0-").append(lastDigit);

        if (maxSymbols > NUMBER_OF_DIGITS) {
            sb.append(", ");
            char finalLetter = (char)('a' + maxSymbols - NUMBER_OF_DIGITS - 1);
            if (finalLetter == 'a') {
                sb.append('a');
            } else {
                sb.append("a-").append(finalLetter);
            }
        }
        sb.append(").");
        System.out.println(sb);
    }
}