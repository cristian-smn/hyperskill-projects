package numbers;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConsoleUI {
    private final Scanner scanner;
    private final NumberService numberService;
    private final RequestValidator requestValidator;

    public ConsoleUI(Scanner scanner, NumberService numberService, RequestValidator requestValidator) {
        this.scanner = scanner;
        this.numberService = numberService;
        this.requestValidator = requestValidator;
    }

    private void printInstructions() {
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

        boolean running = true;
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
                running = handleRequest(userRequest);
            } catch (InvalidRequestException ire) {
                System.out.println(ire.getMessage());
            }
        }
    }

    private boolean handleRequest(UserRequest userRequest) {
        switch (userRequest) {
            case SingleRequest r -> {
                if (r.start() == 0) {
                    exit();
                    return false;
                }
                print(numberService.processRequest(r));
            }
            case ListRequest r -> print(numberService.processRequest(r));
            case ListWithPropertiesRequest r -> print(numberService.processRequest(r));
        }
        return true;
    }

    private void print(NumberResult numberResult) {
        System.out.println("Properties of " + numberResult.number());
        for (Property p : Property.values()) {
            System.out.printf("%s: %b%n", p.displayName(),
                    numberResult.trueProperties().contains(p));
        }
    }

    private void print(List<NumberResult> numbersResult) {
        for (NumberResult numberResult : numbersResult) {
            System.out.println(formatShort(numberResult));
        }
    }

    private String formatShort(NumberResult numberResult) {
        List<String> propertiesList = new ArrayList<>();

        for (Property p : numberResult.trueProperties()) {
            propertiesList.add(p.displayName());
        }

        return numberResult.number() + " is " + String.join(", ", propertiesList);
    }

    private static void exit() {
        System.out.println("Goodbye!");
    }
}