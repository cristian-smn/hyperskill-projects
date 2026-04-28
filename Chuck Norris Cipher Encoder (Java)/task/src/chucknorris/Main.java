package chucknorris;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        boolean running = true;
        while (running) {
            System.out.println("Please input operation (encode/decode/exit):");
            String operation = scanner.nextLine();
            running = handleOperation(operation, scanner);
        }

        scanner.close();
    }

    private static boolean handleOperation(String operation, Scanner scanner) {
        switch (operation) {
            case "encode":
                encode(scanner);
                break;
            case "decode":
                decode(scanner);
                break;
            case "exit":
                exit();
                return false;
            default:
                System.out.println("There is no '" + operation + "' operation");
        }
        return true;
    }

    private static void exit() {
        System.out.println("Bye!");
    }

    private static void encode(Scanner scanner) {
        System.out.println("Input string:");
        String inputStr = scanner.nextLine();
        System.out.println("Encoded string:");
        String binaryStr = toBinary(inputStr);
        System.out.println(binaryToUnaryEncoder(binaryStr));
        System.out.println();
    }

    private static void decode(Scanner scanner) {
        System.out.println("Input encoded string:");
        String inputStr = scanner.nextLine();

        String unaryStr = unaryToBinaryDecoder(inputStr);
        if (unaryStr != null) {
            System.out.println("Decoded string:");
            System.out.println(toChar(unaryStr));
        } else {
            System.out.println("Encoded string is not valid!");
        }
        System.out.println();
    }

    private static String toBinary(String input) {
        StringBuilder binaryCode = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char currentChar = input.charAt(i);
            String binaryStr = String.format("%7s", Integer.toBinaryString(currentChar));
            binaryStr = binaryStr.replace(' ', '0');
            binaryCode.append(binaryStr);
        }
        return binaryCode.toString();
    }

    private static String binaryToUnaryEncoder(String binaryStr) {
        int contor = 0;
        StringBuilder sb = new StringBuilder();
        for (int j = 0; j < binaryStr.length(); j++) {
            if (j > 0 && binaryStr.charAt(j) != binaryStr.charAt(j - 1)) {
                contor = 0;
                sb.append(" ");
            }
            if (contor == 0) {
                String leading = (binaryStr.charAt(j) == '0') ? "00 " : "0 ";
                sb.append(leading);
            }
            contor++;
            sb.append(0);
        }
        return sb.toString();
    }

    private static boolean containsOnlyZeros(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }

        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) != '0') {
                return false;
            }
        }
        return true;
    }

    private static String unaryToBinaryDecoder(String unaryStr) {
        if (!unaryStr.matches("[0 ]+")) {
            return null;
        }

        String[] parts = unaryStr.split(" ");

        if (parts.length % 2 == 1) {
            return null;
        }

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < parts.length; i += 2) {
            String prefix = parts[i];
            String block = parts[i + 1];

            if (!(prefix.equals("0") || prefix.equals("00"))) {
                return null;
            }

            if (!containsOnlyZeros(block)) {
                return null;
            }

            int bit = (prefix.equals("0")) ? 1 : 0;
            sb.repeat(String.valueOf(bit), block.length());
        }

        if (sb.length() % 7 != 0) {
            return null;
        }
        return sb.toString();
    }

    private static String toChar(String binaryStr) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < binaryStr.length(); i += 7) {
            String charStr = binaryStr.substring(i, i + 7);
            char character = (char) Integer.parseInt(charStr, 2);
            sb.append(character);
        }
        return sb.toString();
    }
}