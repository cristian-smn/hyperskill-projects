package numbers;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class ConsoleUI {
    private final Scanner scanner;
    private final AmazingNumbers amazingNumbers;
    private final RequestValidator requestValidator;

    public ConsoleUI(Scanner scanner, AmazingNumbers amazingNumbers, RequestValidator requestValidator) {
        this.scanner = scanner;
        this.amazingNumbers = amazingNumbers;
        this.requestValidator = requestValidator;
    }

    public void printInstructions() {
        System.out.println("""
                Welcome to Amazing Numbers!
                
                Supported requests:
                - enter a natural number to know its properties;
                - enter two natural numbers to obtain the properties of the list:
                  * the first parameter represents a starting number;
                  * the second parameter shows how many consecutive numbers are to be printed;
                - two natural numbers and properties to search for;
                - a property preceded by minus must not be present in numbers;
                - separate the parameters with one space;
                - enter 0 to exit.""");
    }

    public void run() {
        printInstructions();

        boolean running  = true;
        while (running) {
            System.out.println();
            System.out.print("Enter a request: ");
            String request = scanner.nextLine();
            if (request.isBlank()) {
                printInstructions();
                continue;
            }
            UserRequest userRequest;
            try {
                userRequest = requestValidator.validate(request);
            } catch (InvalidRequestException ire) {
                System.out.println(ire.getMessage());
                continue;
            }
            running = requestHandler(userRequest);

        }
    }

    private boolean requestHandler(UserRequest userRequest) {
        switch (userRequest) {
            case SingleRequest r -> {
                if (r.start() == 0) {
                    exit();
                    return false;
                }
                print(amazingNumbers.processRequest(r));
            }
            case ListRequest r -> print(amazingNumbers.processRequest(r));
            case ListWithPropertiesRequest r -> print(amazingNumbers.processRequest(r));
        }
        return true;
    }

    private void print(AmazingNumbers.NumberResult numberResult) {
        long number = numberResult.number();
        Set<Property> trueProperties = numberResult.trueProperties();

        System.out.println("Properties of " + number);
        for (Property p : Property.values()) {
            System.out.printf("%s: %b%n", p.name().toLowerCase(), trueProperties.contains(p));
        }
    }

    private void print(List<AmazingNumbers.NumberResult> numbersResult) {
        for (AmazingNumbers.NumberResult numberResult : numbersResult) {
            long number = numberResult.number();
            Set<Property> trueProperties = numberResult.trueProperties();

            List<String> propertiesList = new ArrayList<>();
            for (Property p : trueProperties) {
                propertiesList.add(p.name().toLowerCase());
            }
            String properties = String.join(", ", propertiesList);
            System.out.println(number + " is " + properties);
        }
    }

    private static void exit() {
        System.out.println("Goodbye!");
    }
}