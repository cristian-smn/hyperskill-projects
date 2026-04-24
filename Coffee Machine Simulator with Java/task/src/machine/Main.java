package machine;

public class Main {
    public static void main(String[] args) {
        CoffeeMachine coffeeMachine = new CoffeeMachine();
        ConsoleUI consoleUI = new ConsoleUI(coffeeMachine);
        consoleUI.run();
    }
}