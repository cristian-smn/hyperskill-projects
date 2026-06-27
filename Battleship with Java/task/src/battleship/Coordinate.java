package battleship;

public class Coordinate {
    private final char row;
    private final int col;

    public Coordinate(char row, int col) {
        this.row = row;
        this.col = col;
    }

    public static Coordinate fromString(String input) {
        if (input == null || input.length() < 2 || input.length() > 3) {
            throw new IllegalArgumentException("Error!");
        }

        char row = input.charAt(0);
        int col;

        try {
            col = Integer.parseInt(input.substring(1));
        } catch (NumberFormatException nfe) {
            throw new IllegalArgumentException("Error!");
        }

        Coordinate coordinate = new Coordinate(row, col);
        if (!coordinate.isValid()) {
            throw new IllegalArgumentException("Error! You entered the wrong coordinates! Try again:");
        }

        return coordinate;
    }

    public boolean isSameRow(Coordinate other) {
        return this.row == other.row;
    }

    public boolean isSameCol(Coordinate other) {
        return this.col == other.col;
    }

    public int getRowIndex() {
        return row - 'A';
    }

    public int getColIndex() {
        return col - 1;
    }

    public char getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean isValid() {
        return row >= 'A' && row <= 'J' && col >= 1 && col <= 10;
    }

    @Override
    public String toString() {
        return row + "" + col;
    }
}
