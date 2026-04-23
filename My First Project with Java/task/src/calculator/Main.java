package calculator;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int bubblegumPrice = 202;
        int toffeePrice = 118;
        int iceCreamPrice = 2250;
        int milkChocolatePrice = 1680;
        int doughnutPrice = 1075;
        int pancakePrice = 80;

        int totalIncome = bubblegumPrice + toffeePrice + iceCreamPrice +
                milkChocolatePrice + doughnutPrice + pancakePrice;

        System.out.println("Earned amount:");
        System.out.println("Bubblegum: $" + bubblegumPrice);
        System.out.println("Toffee: $" + toffeePrice);
        System.out.println("Ice cream: $" + iceCreamPrice);
        System.out.println("Milk chocolate: $" + milkChocolatePrice);
        System.out.println("Doughnut: $" + doughnutPrice);
        System.out.println("Pancake: $" + pancakePrice);
        System.out.println();
        System.out.println("Income: $" + totalIncome);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Staff expenses:");
        int staffExpenses = scanner.nextInt();
        System.out.println("Other expenses:");
        int otherExpenses = scanner.nextInt();

        int netIncome = totalIncome - staffExpenses - otherExpenses;
        System.out.printf("Net income: $%d", netIncome);

        scanner.close();
    }
}