package lastpencil;

public class Main {
    public static void main(String[] args) {
        LastPencil lastPencil = new LastPencil();
        ConsoleUI consoleUI = new ConsoleUI(lastPencil);
        consoleUI.run();
    }
}