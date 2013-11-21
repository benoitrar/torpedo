package com.epam.livingpope.torpedo.torpedo;

import java.util.ArrayList;

import com.epam.livingpope.torpedo.communication.Status;
import com.epam.livingpope.torpedo.shapes.Point;
import com.epam.livingpope.torpedo.shapes.Ship;

public class RandomTorpedo implements Torpedo {

    private ArrayList<Ship> ships;
    private final char[][] tableWithShips;

    public RandomTorpedo(ArrayList<Ship> ships, int tableWidth, int tableHeight) {
        this.ships = ships;
        tableWithShips = createTableWithShips(tableWidth, tableHeight);
    }

    public Status fire(Point target) {
        Status result = Status.MISS;
        for (Ship ship : ships) {
            if (ship.hasPoint(target)) {
                if (hitShip(target, ship)) {
                    result = Status.SUNK;
                } else {
                    result = Status.HIT;
                }
                if (isGameOver()) {
                    result = Status.WIN;
                }
                break;
            }
        }
        return result;
    }

    private boolean hitShip(Point target, Ship ship) {
        boolean sunk = false;
        ship.hitPoint(target);
        if (ship.isSunk()) {
            ships.remove(ship);
            sunk = true;
        }
        return sunk;
    }

    public boolean isGameOver() {
        boolean result = false;
        if (ships.isEmpty()) {
            result = true;
        }
        return result;
    }

    private char[][] createTableWithShips(int tableWidth, int tableHeight) {
        char[][] table = new char[tableWidth][tableHeight];

        setDefaultValuesForPrint(tableWidth, tableHeight, table);

        setAllShipPointsForPrint(table);

        return table;
    }

    public char[][] getTableWithShips(int tableWidth, int tableHeight) {
        return tableWithShips;
    }

    private void setAllShipPointsForPrint(char[][] table) {
        for (Ship ship : ships) {
            for (Point point : ship.getPointList()) {
                table[point.x][point.y] = 'X';
            }
        }
    }

    private void setDefaultValuesForPrint(int tableWidth, int tableHeight, char[][] table) {
        for (int i = 0; i < tableWidth; i++) {
            for (int j = 0; j < tableHeight; j++) {
                table[i][j] = '.';
            }
        }
    }

}
