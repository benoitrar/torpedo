package com.epam.livingpope.torpedo.torpedo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import com.epam.livingpope.torpedo.communication.GameStatus;
import com.epam.livingpope.torpedo.shapes.FieldState;
import com.epam.livingpope.torpedo.shapes.GameBoard;
import com.epam.livingpope.torpedo.shapes.Hittable;
import com.epam.livingpope.torpedo.shapes.Point;
import com.epam.livingpope.torpedo.shapes.Ship;
import com.epam.livingpope.torpedo.shapes.ShipShape;

public class CleverTorpedo implements Torpedo, Hittable {

    private static final int SHIP_HEIGHT = 4;
    private static final int SHIP_WIDTH = 4;

    public final GameBoard board;
    private final ArrayList<Ship> ships = new ArrayList<>();

    private CleverTorpedo(int width, int height) {
        board = new GameBoard(width, height, FieldState.UNHIT_EMPTY);
    }

    public static class Builder {

        private int width;
        private int height;
        private CleverTorpedo torpedo;

        public Builder(int width, int height) {
            this.width = width;
            this.height = height;
            torpedo = new CleverTorpedo(width, height);
        }

        public Builder addShip(ShipShape shape) {
            Ship ship = getPlaceableShip(shape);
            // TODO board
            System.out.println(ship);
            torpedo.ships.add(ship);
            torpedo.board.add(ship);
            return this;
        }

        public Builder readShipsFromFile(String fileName) {
            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                processFile(reader);
            } catch (IOException e) {
                System.err.println("Error while reading from file " + fileName);
                e.printStackTrace();
            }
            return this;
        }

        private void processFile(BufferedReader reader) throws IOException {
            String line = "";
            while ((line = reader.readLine()) != null) {
                int shipCount = Integer.parseInt(line);
                FieldState[][] values = getValues(reader);
                addShips(shipCount, values);
            }
        }

        public CleverTorpedo build() {
            return torpedo;
        }

        private Ship getPlaceableShip(ShipShape shape) {
            Ship ship;
            Random random = new Random();
            // TODO could be better
            do {
                int offsetX = random.nextInt(width - SHIP_WIDTH);
                int offsetY = random.nextInt(height - SHIP_HEIGHT);
                ship = new Ship(shape, offsetX, offsetY);
            } while (conflictsWithOthers(ship));
            return ship;
        }

        private boolean conflictsWithOthers(Ship ship) {
            boolean result = false;
            for (Ship s : torpedo.ships) {
                if (s.hasConflictWithShip(ship)) {
                    result = true;
                    break;
                }
            }
            return result;
        }

        private FieldState[][] getValues(BufferedReader reader) throws IOException {
            FieldState[][] values = new FieldState[SHIP_WIDTH][SHIP_HEIGHT];
            String line;
            for (int rowIndex = 0; rowIndex < SHIP_WIDTH; rowIndex++) {
                line = reader.readLine().toUpperCase();
                for (int colIndex = 0; colIndex < SHIP_HEIGHT; colIndex++) {
                    if (line.charAt(colIndex) == 'X') {
                        values[rowIndex][colIndex] = FieldState.UNHIT_SHIP;
                    } else {
                        values[rowIndex][colIndex] = FieldState.UNHIT_EMPTY;
                    }
                }
            }
            return values;
        }

        private void addShips(int shipCount, FieldState[][] values) {
            for (int counter = 0; counter < shipCount; counter++) {
                addShip(new ShipShape(values));
            }
        }
    }

    @Override
    public boolean isHittable(Point point) {
        boolean result = false;
        for (Ship ship : ships) {
            if (ship.isHittable(point)) {
                result = true;
            }
        }
        return result;
    }

    @Override
    public void hit(Point point) {
        for (Ship ship : ships) {
            if (ship.isHittable(point)) {
                ship.hit(point);
                break;
            }
        }
    }

    @Override
    public GameStatus getFireResult(Point target) {
        GameStatus result = GameStatus.MISS;
        for (Ship ship : ships) {
            if (ship.isHittable(target)) {
                ship.hit(target);
                System.out.println("hit: " + target);
                if (!ship.isSunk()) {
                    result = GameStatus.HIT;
                } else {
                    System.out.println("sunk: " + ship);
                    ships.remove(ship);
                    result = GameStatus.SUNK;
                }
                if (isGameOver()) {
                    result = GameStatus.WIN;
                }
                System.out.println(ships);
                break;
            }
        }
        System.out.println(board);
        return result;
    }

    private boolean isGameOver() {
        return ships.isEmpty();
    }
}
