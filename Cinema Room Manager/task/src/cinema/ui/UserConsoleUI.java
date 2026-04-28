package cinema.ui;

import cinema.domain.Cinema;
import cinema.domain.exceptions.InvalidSeatException;
import cinema.utils.InputUtils;

public class UserConsoleUI {
    private final Cinema cinema;
    private final InputUtils inputUtils;

    public UserConsoleUI(Cinema cinema, InputUtils inputUtils) {
        this.cinema = cinema;
        this.inputUtils = inputUtils;
    }

    public void run() {
        boolean isExit = false;
        while (!isExit) {
            System.out.println();
            System.out.println("1. Show the seats");
            System.out.println("2. Buy a ticket");
            System.out.println("3. Statistics");
            System.out.println("0. Exit");

            int command = inputUtils.readInt();

            switch (command) {
                case 0:
                    isExit = true;
                    break;
                case 1:
                    draw();
                    break;
                case 2:
                    buySeat();
                    break;
                case 3:
                    getStatistics();
                    break;
            }
        }
    }

    private void buySeat() {
        while (true) {
            System.out.println("Enter a row number:");
            int row = inputUtils.readInt();
            int number = inputUtils.readInt();
            System.out.println();

            try {
                cinema.buy(row, number);
                System.out.println("Ticket price: $" + cinema.getSeatPrice(row));
                break;
            } catch (InvalidSeatException ise) {
                System.out.println(ise.getMessage());
            }
        }
    }

    private void getStatistics() {
        System.out.println("Number of purchased tickets: " + cinema.getTicketsSold());
        System.out.printf("Percentage: %.2f%%\n", cinema.getSalesPercentage());
        System.out.println("Current income: $" + cinema.getCurrentIncome());
        System.out.println("Total income: $" + cinema.getTotalIncome());
    }


    private void draw() {
        char[][] layout = cinema.getLayout();
        System.out.println("Cinema: ");
        System.out.print(" ");
        for (int i = 0; i < layout[0].length; i++) {
            System.out.print(" ");
            System.out.print(i + 1);
        }
        System.out.println();
        for (int i = 0; i < layout.length; i++) {
            System.out.print(i + 1);
            System.out.print(" ");
            for (int j = 0; j < layout[i].length; j++) {
                System.out.print(layout[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
