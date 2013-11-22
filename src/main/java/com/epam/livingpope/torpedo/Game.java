package com.epam.livingpope.torpedo;

import java.util.ArrayList;
import java.util.List;

import com.epam.livingpope.torpedo.communication.BulletStatus;
import com.epam.livingpope.torpedo.shapes.Point;
import com.epam.livingpope.torpedo.shapes.Ship;
import com.epam.livingpope.torpedo.shapes.ShipBuilder;
import com.epam.livingpope.torpedo.targeting.RandomTargetingSystem;
import com.epam.livingpope.torpedo.torpedo.RandomTorpedo;
import com.epam.livingpope.torpedo.torpedo.Torpedo;

public class Game {

    private static final String DEFAULT_FILE_FOR_SHIPS = "C:/Users/Benedek_Kiss@epam.com/Desktop/ships2.txt";
    private Torpedo torpedo;
    private RandomTargetingSystem targetingSystem;
    private List<Point> firedPoints = new ArrayList<>();

    private int fireCounter = 0;

    public Game(RandomTargetingSystem targetingSystem) {
        this.targetingSystem = targetingSystem;
        torpedo = new RandomTorpedo(readShips(), targetingSystem.getTableWidth(), targetingSystem.getTableHeight());
        printTable();
    }

    public void printTable() {
        int tableWidth = targetingSystem.getTableWidth();
        int tableHeight = targetingSystem.getTableHeight();
        char[][] tableWithShips = torpedo.getTableWithShips();

        char[][] tableWithShipsAndFiredPoints = addFiredPoints(tableWithShips);

        printTable(tableWidth, tableHeight, tableWithShipsAndFiredPoints);
    }

    private char[][] addFiredPoints(char[][] table) {
        char tableWithFiredPoints[][] = copy(table);
        for (Point point : firedPoints) {
            if (tableWithFiredPoints[point.x][point.y] == Point.SHIP_POINT) {
                tableWithFiredPoints[point.x][point.y] = Point.HIT_SHIP_POINT;
            } else {
                tableWithFiredPoints[point.x][point.y] = Point.MISSED_POINT;
            }
        }
        return tableWithFiredPoints;
    }

    private char[][] copy(char[][] table) {
        char[][] copy = new char[table[0].length][table.length];

        for (int i = 0; i < table[0].length; i++) {
            for (int j = 0; j < table.length; j++) {
                copy[i][j] = table[i][j];
            }
        }

        return copy;
    }

    private void printTable(int tableWidth, int tableHeight, char[][] table) {
        for (int i = 0; i < tableWidth; i++) {
            for (int j = 0; j < tableHeight; j++) {
                if (table[i][j] == Point.HIT_SHIP_POINT || table[i][j] == Point.SHIP_POINT) {
                    System.err.print(" " + table[i][j] + " ");
                } else {
                    System.out.print(" " + table[i][j] + " ");
                }
            }
            System.out.print("\n");
        }
    }

    @SuppressWarnings("unused")
    private ArrayList<Ship> createShips() {
        ArrayList<Ship> result = new ArrayList<Ship>();
        for (int i = 0; i < 4; i++) {
            Ship ship = targetingSystem.generateShip();
            while (hasConflictWithOthers(ship, result)) {
                ship = targetingSystem.generateShip();
            }
            result.add(ship);
        }
        return result;
    }

    private ArrayList<Ship> readShips() {
        ShipBuilder sb = new ShipBuilder(targetingSystem);
        return sb.shipsFromFile(DEFAULT_FILE_FOR_SHIPS);
    }

    private boolean hasConflictWithOthers(Ship ship, List<Ship> shipList) {
        boolean result = false;
        for (Ship ourShip : shipList) {
            if (ourShip.hasConflictWithShip(ship)) {
                result = true;
            }
        }
        return result;
    }

    public void startGame() {
        while (!torpedo.isGameOver()) {
            Point point = targetingSystem.generatePoint();
            getStatusOnFire(point);
        }
        printResult();
    }

    public BulletStatus getStatusOnFire(Point target) {
        fireCounter++;
        firedPoints.add(target);
        BulletStatus status = torpedo.fire(target);
        if (status.equals(BulletStatus.WIN)) {
            printResult();
        }
        return status;
    }

    public void printResult() {
        System.out.println("Counter: " + fireCounter);
    }

    public Point firstTarget() {
        return targetingSystem.firstTarget();
    }

    public Point nextTarget() {
        return targetingSystem.nextTarget();
    }

    public void onHit() {
        targetingSystem.onHit();
    }

    public void onMiss() {
        targetingSystem.onMiss();
    }

    public void onSunk() {
        targetingSystem.onSunk();
    }

}
