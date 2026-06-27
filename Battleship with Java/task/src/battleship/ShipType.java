package battleship;

public enum ShipType {
    AIRCRAFT_CARRIER("Aircraft Carrier", 5),
    BATTLESHIP("Battleship", 4),
    SUBMARINE("Submarine", 3),
    CRUISER("Cruiser", 3),
    DESTROYER("Destroyer", 2);

    private final String label;
    private final int length;

    ShipType(String label, int length) {
        this.label = label;
        this.length = length;
    }

    public String getLabel() {
        return label;
    }

    public int getLength() {
        return length;
    }
}
