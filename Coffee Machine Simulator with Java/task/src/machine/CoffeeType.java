package machine;

public enum CoffeeType {
    ESPRESSO(250, 0, 16, 4),
    LATTE(350, 75, 20, 7),
    CAPPUCCINO(200, 100, 12, 6);

    private final int water;
    private final int milk;
    private final int coffeeBeans;
    private final int price;

    CoffeeType(int water, int milk, int coffeeBeans, int price) {
        this.water = water;
        this.milk = milk;
        this.coffeeBeans = coffeeBeans;
        this.price = price;
    }

    public int getWater() {
        return water;
    }

    public int getMilk() {
        return milk;
    }

    public int getCoffeeBeans() {
        return coffeeBeans;
    }

    public int getPrice() {
        return price;
    }
}