package com.epam.livingpope.torpedo;

import java.util.ArrayList;
import java.util.List;

import com.epam.livingpope.torpedo.communication.Status;
import com.epam.livingpope.torpedo.shapes.Point;
import com.epam.livingpope.torpedo.shapes.Ship;
import com.epam.livingpope.torpedo.shapes.ShipBuilder;
import com.epam.livingpope.torpedo.target.RandomTargetingSystem;
import com.epam.livingpope.torpedo.torpedo.RandomTorpedo;
import com.epam.livingpope.torpedo.torpedo.Torpedo;

public class Game {

    private static final String DEFAULT_FILE_FOR_SHIPS = "D:/prj/clean-pair/torpedo/resources/test.in";
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
        char[][] tableWithShips = torpedo.getTableWithShips(tableWidth, tableHeight);
        addFiredPoints(tableWithShips);

        printTable(tableWidth, tableHeight, tableWithShips);
    }

    private void addFiredPoints(char[][] table) {
        for (Point point : firedPoints) {
            if (table[point.x][point.y] == 'X') {
                table[point.x][point.y] = '#';
            } else {
                table[point.x][point.y] = 'O';
            }
        }
    }

    private void printTable(int tableWidth, int tableHeight, char[][] table) {
        for (int i = 0; i < tableWidth; i++) {
            for (int j = 0; j < tableHeight; j++) {
                System.out.print(" " + table[i][j] + " ");
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
            fire(point);
        }
        printResult();
    }

    public Status fire(Point target) {
        fireCounter++;
        firedPoints.add(target);
        Status status = torpedo.fire(target);
        if (status.equals(Status.WIN)) {
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
