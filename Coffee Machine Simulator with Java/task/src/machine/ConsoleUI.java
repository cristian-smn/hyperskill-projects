package machine;

import java.util.Scanner;

public class ConsoleUI {
    private final CoffeeMachine coffeeMachine;

    public ConsoleUI(CoffeeMachine coffeeMachine) {
        this.coffeeMachine = coffeeMachine;
    }
    public void run() {
        Scanner scanner = new Scanner(System.in);
        boolean isExit = false;

        while (!isExit) {
            System.out.println("Write action (buy, fill, take, clean, remaining, exit):");
            String action = scanner.nextLine();
            switch (action) {
                case "buy":
                    if (coffeeMachine.isClean()) {
                        buy(scanner);
                    } else {
                        System.out.println("I need cleaning!");
                    }
                    break;
                case "clean":
                    clean();
                    break;
                case "fill":
                    fill(scanner);
                    break;
                case "take":
                    take();
                    break;
                case "remaining":
                    printMachineState();
                    break;
                case "exit":
                    isExit = true;
                    break;
                default:
                    System.out.println("Wrong input! Try again: ");
                    break;
            }
        }
        scanner.close();
    }

    private int getUserInput(Scanner scanner) {
        while(!scanner.hasNextInt()) {
            scanner.next();
            System.out.println("Wrong input! Try again: ");
        }
        return scanner.nextInt();
    }
    private void fill(Scanner scanner) {
        System.out.println("Write how many ml of water you want to add:");
        int water = getUserInput(scanner);
        System.out.println("Write how many ml of milk you want to add:");
        int milk = getUserInput(scanner);
        System.out.println("Write how many grams of coffee beans you want to add:");
        int coffeeBeans = getUserInput(scanner);
        System.out.println("Write how many disposable cups you want to add:");
        int cups = getUserInput(scanner);
        scanner.nextLine();

        coffeeMachine.fill(water, milk, coffeeBeans, cups);
        System.out.println();
    }

    private void take() {
        System.out.println("I gave you $" + coffeeMachine.getMoney());
        coffeeMachine.take();
        System.out.println();
    }

    private void buy(Scanner scanner) {
        System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:");
        CoffeeType coffeeType = null;

        boolean isOk = false;
        while (!isOk) {
            String userCoffee = scanner.nextLine();

            switch (userCoffee) {
                case "1":
                    coffeeType = CoffeeType.ESPRESSO;
                    isOk = true;
                    break;
                case "2":
                    coffeeType = CoffeeType.LATTE;
                    isOk = true;
                    break;
                case "3":
                    coffeeType = CoffeeType.CAPPUCCINO;
                    isOk = true;
                    break;
                case "back":
                    return;
                default:
                    System.out.println("Wrong input! Try again: ");
                    break;
            }
        }

        CoffeeMachine.ResourceStatus resourceStatus = coffeeMachine.checkInventory(coffeeType);
        if (resourceStatus == CoffeeMachine.ResourceStatus.ENOUGH) {
            System.out.println("I have enough resources, making you a coffee!");
            coffeeMachine.buy(coffeeType);
        } else {
            System.out.printf("Sorry, not enough %s!", resourceStatus.getValue());
        }
        System.out.println();
    }

    private void printMachineState() {
        System.out.println("The coffee machine has:");
        System.out.println(coffeeMachine.getWater() + " ml of water");
        System.out.println(coffeeMachine.getMilk() + " ml of milk");
        System.out.println(coffeeMachine.getCoffeeBeans() + " g of coffee beans");
        System.out.println(coffeeMachine.getCups() + " disposable cups");
        System.out.println("$" + coffeeMachine.getMoney() + " of money");
        System.out.println();
    }

    private void clean() {
        coffeeMachine.clean();
        System.out.println("I have been cleaned!");
    }
}