package cinema.ui;

import cinema.model.CinemaConfig;
import cinema.utils.InputUtils;

public class AdminConsoleUI {
    public static CinemaConfig setup(InputUtils inputUtils) {
        System.out.println("Enter the number of rows:");
        int rows = inputUtils.readInt();
        System.out.println("Enter the number of seats in each row:");
        int seats = inputUtils.readInt();
        return new CinemaConfig(rows, seats);
    }
}