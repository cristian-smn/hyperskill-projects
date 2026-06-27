package battleship;

import java.util.ArrayList;
import java.util.List;

public class Ship {
    private final Coordinate coordinate1;
    private final Coordinate coordinate2;
    private final ShipType shipType;
    private final boolean isHorizontal;


    public Coordinate getCoordinate1() {
        return coordinate1;
    }

    public Coordinate getCoordinate2() {
        return coordinate2;
    }

    public ShipType getShipType() {
        return shipType;
    }

    public boolean isHorizontal() {
        return isHorizontal;
    }

    public Ship(ShipType shipType, Coordinate coordinate1, Coordinate coordinate2) {
        if (shipType == null || coordinate1 == null || coordinate2 == null) {
            throw new IllegalArgumentException("Error!");
        }
        if (!coordinate1.isSameRow(coordinate2) && !coordinate1.isSameCol(coordinate2)) {
            throw new IllegalArgumentException("Error! Wrong ship location! Try again:");
        }
        if (calculateLength(coordinate1, coordinate2) != shipType.getLength()) {
            throw new IllegalArgumentException("Error! Wrong length of the "
                    + shipType.getLabel() + "! Try again:");
        }

        this.shipType = shipType;
        this.coordinate1 = coordinate1;
        this.coordinate2 = coordinate2;
        this.isHorizontal = coordinate1.isSameRow(coordinate2);
    }

    public int getLength() {
        return calculateLength(coordinate1, coordinate2);
    }

    private static int calculateLength(Coordinate coordinate1, Coordinate coordinate2) {
        if (coordinate1.isSameRow(coordinate2)) {
            return Math.abs(coordinate1.getColIndex() - coordinate2.getColIndex()) + 1;
        }

        return Math.abs(coordinate1.getRowIndex() - coordinate2.getRowIndex()) + 1;
    }

    public List<Coordinate> getParts() {
        List<Coordinate> parts = new ArrayList<>();
        if (isHorizontal) {
            int step = coordinate1.getColIndex() <= coordinate2.getColIndex() ? 1 : -1;

            for (int col = coordinate1.getCol(); col != coordinate2.getCol() + step; col += step) {
                parts.add(new Coordinate(coordinate1.getRow(), col));
            }
        } else {
            int step = coordinate1.getRowIndex() <= coordinate2.getRowIndex() ? 1 : -1;

            for (char row = coordinate1.getRow(); row != coordinate2.getRow() + step; row += (char) step) {
                parts.add(new Coordinate(row, coordinate1.getCol()));
            }
        }

        return parts;
    }
}
