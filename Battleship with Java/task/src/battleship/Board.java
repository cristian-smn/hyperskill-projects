package battleship;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {
    public enum ShotStatus {
        HIT,
        MISSED,
        SUNK
    }

    private static final int MAP_DIM = 10;
    private char[][] layout;
    private Map<Ship, Boolean> ships;

    public char[][] getLayout() {
        return layout;
    }

    public void setLayout(char[][] layout) {
        this.layout = layout;
    }

    public Board() {
        layout = new char[MAP_DIM][MAP_DIM];
        ships = new HashMap<>();
        initialize();
    }

    private void initialize() {
        for (int i = 0; i < layout.length; i++) {
            for (int j = 0; j < layout[i].length; j++) {
                layout[i][j] = '~';
            }
        }
    }

    public ShotStatus shoot(Coordinate shot) {
        if (layout[shot.getRowIndex()][shot.getColIndex()] == '~') {
            layout[shot.getRowIndex()][shot.getColIndex()] = 'M';
            return ShotStatus.MISSED;
        } else {
            layout[shot.getRowIndex()][shot.getColIndex()] = 'X';
            if (isShipSunk()) {
                return ShotStatus.SUNK;
            }
            return ShotStatus.HIT;
        }
    }

    public boolean isGameOver() {
        for (boolean key : ships.values()) {
            if (!key) {
                return false;
            }
        }
        return true;
    }

    public boolean isShipSunk() {
        for (Map.Entry<Ship, Boolean> ship : ships.entrySet()) {
            if (ship.getValue() == true) {
                continue;
            }
            List<Coordinate> parts = ship.getKey().getParts();
            for (int i = 0; i < parts.size(); i++) {
                int row = parts.get(i).getRowIndex();
                int col = parts.get(i).getColIndex();

                if (layout[row][col] != 'X') {
                    break;
                }

                if (i == parts.size() - 1) {
                    ships.replace(ship.getKey(), true);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isAdjacent(Ship ship) {
        List<Coordinate> shipParts = ship.getParts();
        for (Coordinate part : shipParts) {
            for (int dr = -1; dr <= 1; dr++) {
                for (int dc = -1; dc <= 1; dc++) {
                    int newRow = part.getRowIndex() + dr;
                    int newCol = part.getColIndex() + dc;
                    if (newCol >= 0 && newCol < MAP_DIM && newRow >= 0
                            && newRow < MAP_DIM && layout[newRow][newCol] != '~') {
                        throw new IllegalArgumentException("Error! You placed it too close to another one. Try again:");
                    }
                }
            }
        }
        return false;
    }

    public void addShip(Ship ship) {
        ships.put(ship, false);
        List<Coordinate> shipParts = ship.getParts();
        for (Coordinate part : shipParts) {
            layout[part.getRowIndex()][part.getColIndex()] = 'O';
        }
    }
}
