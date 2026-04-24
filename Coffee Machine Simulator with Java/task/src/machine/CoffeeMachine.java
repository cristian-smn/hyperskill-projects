package machine;

public class CoffeeMachine {
    private final static int CUPS_UNTIL_CLEAN = 10;
    public enum ResourceStatus {
        ENOUGH(""),
        NOT_ENOUGH_WATER("water"),
        NOT_ENOUGH_MILK("milk"),
        NOT_ENOUGH_COFFEE_BEANS("coffee beans"),
        NOT_ENOUGH_CUPS("cups");

        private final String value;

        ResourceStatus(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
    private int water = 400;
    private int milk = 540;
    private int coffeeBeans = 120;
    private int cups = 9;
    private int cupsUsed = 0;
    private int money = 550;

    public void fill(int water, int milk, int coffeeBeans, int cups) {
        this.water += water;
        this.milk += milk;
        this.coffeeBeans += coffeeBeans;
        this.cups += cups;
    }

    public void take() {
        money = 0;
    }

    public void buy(CoffeeType coffeeType) {
        water -= coffeeType.getWater();
        milk -= coffeeType.getMilk();
        coffeeBeans -= coffeeType.getCoffeeBeans();
        cups--;
        cupsUsed++;
        money += coffeeType.getPrice();
    }

    public boolean isClean() {
        return cupsUsed < CUPS_UNTIL_CLEAN;
    }

    public void clean() {
        cupsUsed = 0;
    }

    public ResourceStatus checkInventory(CoffeeType coffeeType) {
        if (water < coffeeType.getWater())
            return ResourceStatus.NOT_ENOUGH_WATER;
        if (milk < coffeeType.getMilk())
            return ResourceStatus.NOT_ENOUGH_MILK;
        if (coffeeBeans < coffeeType.getCoffeeBeans())
            return ResourceStatus.NOT_ENOUGH_COFFEE_BEANS;
        if (cups == 0)
            return ResourceStatus.NOT_ENOUGH_CUPS;

        return ResourceStatus.ENOUGH;
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

    public int getCups() {
        return cups;
    }

    public int getMoney() {
        return money;
    }
}