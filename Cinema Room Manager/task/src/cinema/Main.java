package cinema;

import cinema.domain.Cinema;
import cinema.model.CinemaConfig;
import cinema.ui.AdminConsoleUI;
import cinema.ui.UserConsoleUI;
import cinema.utils.InputUtils;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            InputUtils inputUtils = new InputUtils(scanner);

            /*
             * In acest caz InputUtils este doar un Scanner wrapper, dar daca decid sa schimb
             * logica de citire, o pot face intr un singur loc, sau daca este nevoie de suport
             * multithreadding centralizat, in acest context insa, este complet schimbabil cu
             * trimiterea unui Scanner
             * */

            CinemaConfig config = AdminConsoleUI.setup(inputUtils);
            Cinema cinema = new Cinema(config);

            UserConsoleUI userConsoleUI = new UserConsoleUI(cinema, inputUtils);
            userConsoleUI.run();
        }
    }
}