package com.epam.livingpope.torpedo.shapes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class GameBoard implements Hittable {
    
    private static final char SHIP_POINT = 'X';
    private static final int SHIP_HEIGHT = 4;
    private static final int SHIP_WIDTH = 4;
    
    public final int width;
    public final int height;
    private final ArrayList<Ship> ships = new ArrayList<>();

    private GameBoard(int width, int height) {
        this.width = width;
        this.height = height;
    }
    
    public static class Builder {
        private GameBoard board;
        
        public Builder(int width, int height) {
            board = new GameBoard(width, height);
        }
        
        public Builder addShip(ShipShape shape) {
            Random random = new Random();
            int offsetX = random.nextInt(board.width);
            int offsetY = random.nextInt(board.height);
            Ship ship = getPlaceableShip(shape, offsetX, offsetY);
            board.ships.add(ship);
            return this;
        }
        
        public Builder readShipsFromFile(String fileName) {
            try (
                BufferedReader reader = new BufferedReader(new FileReader(fileName));
            ){
                processFile(reader);
            } catch (IOException e) {
                System.err.println("Error while reading from file " + fileName);
                e.printStackTrace();
            }
            return this;
        }
        
        public GameBoard build() {
            return board;
        }

        private Ship getPlaceableShip(ShipShape shape, int offsetX, int offsetY) {
            Ship ship;
            do {
                ship = new Ship(shape, offsetX, offsetY);
            } while (conflictsWithOthers(ship));
            return ship;
        }

        private boolean conflictsWithOthers(Ship ship) {
            boolean result = false;
            for (Ship s : board.ships) {
                if (s.hasConflictWithShip(ship)) {
                    result = true;
                    break;
                }
            }
            return result;
        }

        private void processFile(BufferedReader reader) throws IOException {
            String line = "";
            while ((line = reader.readLine()) != null) {
                int shipCount = Integer.parseInt(line);
                boolean[][] values = getValues(reader);
                addShips(shipCount, values);
            }
        }

        private boolean[][] getValues(BufferedReader reader) throws IOException {
            boolean[][] values = new boolean[SHIP_WIDTH][SHIP_HEIGHT];
            String line;
            for (int rowIndex = 0; rowIndex < SHIP_WIDTH; rowIndex++) {
                line = reader.readLine().toUpperCase();
                for (int colIndex = 0; colIndex < SHIP_HEIGHT; colIndex++) {
                    values[rowIndex][colIndex] = getValue(line, colIndex);
                }
            }
            return values;
        }

        private boolean getValue(String shipLine, int colIndex) {
            return shipLine.charAt(colIndex) == SHIP_POINT;
        }

        private void addShips(int shipCount, boolean[][] values) {
            for(int counter=0;counter<shipCount;counter++) {
                addShip(new ShipShape(values));
            }
        }
    }

    public boolean isOnTable(Point point) {
        return isOnTable(point.x, point.y);
    }

    public boolean isOnTable(int x, int y) {
        boolean result = false;
        if (width > x && x >= 0 && height > y && y >= 0) {
            result = true;
        }
        return result;
    }

    @Override
    public boolean isHittable(Point point) {
        boolean result = false;
        for (Ship ship : ships) {
            if(ship.isHittable(point)) {
                result = true;
            }
        }
        return result;
    }

    @Override
    public void hit(Point point) {
        for (Ship ship : ships) {
            if(ship.isHittable(point)) {
                ship.hit(point);
                break;
            }
        }
    }
}
