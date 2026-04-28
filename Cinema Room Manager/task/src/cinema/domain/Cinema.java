package cinema.domain;

import cinema.domain.exceptions.PurchasedSeatException;
import cinema.domain.exceptions.SeatOutOfBoundsException;
import cinema.model.CinemaConfig;

import java.util.Arrays;

public class Cinema {
    private final int seats;
    private final int rows;
    private final char[][] layout;
    private int ticketsSold;
    private int currentIncome;
    private final int totalIncome;

    public char[][] getLayout() {
        char[][] copy = new char[rows][seats];
        for (int i = 0; i < layout.length; i++) {
            System.arraycopy(layout[i], 0, copy[i], 0, layout[i].length);
        }
        return copy;
    }

    public int getTicketsSold() {
        return ticketsSold;
    }

    public int getCurrentIncome() {
        return currentIncome;
    }

    public int getTotalIncome() {
        return totalIncome;
    }

    public Cinema(CinemaConfig config) {
        this.rows = config.rows();
        this.seats = config.seats();
        this.layout = new char[rows][seats];
        initialize(layout);
        ticketsSold = 0;
        currentIncome = 0;
        totalIncome = calculateTotalIncome();
    }

    private static void initialize(char[][] layout) {
        for (char[] chars : layout) {
            Arrays.fill(chars, 'S');
        }
    }

    public double getSalesPercentage() {
        return  ((double) ticketsSold / (rows * seats)) * 100;
    }

    private int calculateTotalIncome() {
        int totalIncome = 0;
        for (int i = 1; i <= rows; i++) {
            totalIncome += seats * getSeatPrice(i);
        }
        return totalIncome;
    }

    public void buy(int row, int number) {
        if (row < 1 || row > rows || number < 1 || number > seats) {
            throw new SeatOutOfBoundsException("Wrong input!");
        }
        if (layout[row - 1][number - 1] == 'B') {
            throw new PurchasedSeatException("That ticket has already been purchased!");
        }
        layout[row - 1][number - 1] = 'B';
        ticketsSold++;
        currentIncome += getSeatPrice(row);
    }

    public int getSeatPrice(int row) {
        if (rows * seats <= 60) {
            return 10;
        }
        int firstHalf = rows / 2;
        return (row <= firstHalf) ? 10 : 8;
    }
}