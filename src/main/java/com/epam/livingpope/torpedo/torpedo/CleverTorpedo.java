package com.epam.livingpope.torpedo.torpedo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epam.livingpope.torpedo.communication.GameStatus;
import com.epam.livingpope.torpedo.shapes.FieldState;
import com.epam.livingpope.torpedo.shapes.GameBoard;
import com.epam.livingpope.torpedo.shapes.Point;
import com.epam.livingpope.torpedo.shapes.Ship;
import com.epam.livingpope.torpedo.shapes.ShipShape;

/**
 * Class for...
 *
 * @author Livia_Erdelyi Benedek_Kiss
 */
public final class CleverTorpedo implements Torpedo {

    private static final int SHIP_HEIGHT = 4;
    private static final int SHIP_WIDTH = 4;

    private final Logger logger = LoggerFactory.getLogger(CleverTorpedo.class);

    private final GameBoard board;
    private final List<Ship> ships = new ArrayList<>();

    private CleverTorpedo(int width, int height) {
        board = new GameBoard(width, height, FieldState.UNHIT_EMPTY);
    }

    @Override
    public GameStatus getFireResult(Point target) {
        board.hit(target);
        GameStatus result = GameStatus.MISS;
        for (Ship ship : ships) {
            if (ship.isHittable(target)) {
                ship.hit(target);
                if (!ship.isSunk()) {
                    result = GameStatus.HIT;
                } else {
                    ships.remove(ship);
                    result = GameStatus.SUNK;
                    logger.info("{}", board);
                }
                if (isGameOver()) {
                    result = GameStatus.WIN;
                }
                break;
            }
        }
        return result;
    }

    private boolean isGameOver() {
        return ships.isEmpty();
    }

    public GameBoard getBoard() {
        return board;
    }

    /**
     * Class for...
     *
     * @author Livia_Erdelyi Benedek_Kiss
     */
    public static class Builder {

        private int width;
        private int height;
        private CleverTorpedo torpedo;
        private final Logger logger = LoggerFactory.getLogger(CleverTorpedo.Builder.class);

        /**
         * Creates a CleverTorpedo with params.
         *
         * @param width
         *            width of the torpedo
         * @param height
         *            height of the torpedo
         */
        public Builder(int width, int height) {
            this.width = width;
            this.height = height;
            torpedo = new CleverTorpedo(width, height);
        }

        /**
         * Adds a ship to the torpedo.
         *
         * @param shape
         *            shape of the ship
         * @return the builder
         */
        public Builder addShip(ShipShape shape) {
            Ship ship = getPlaceableShip(shape);
            torpedo.ships.add(ship);
            torpedo.board.add(ship);
            return this;
        }

        /**
         * Reads ships and adds them one by one.
         *
         * @param fileName
         *            full name of the file
         * @return the builder
         */
        public Builder readShipsFromFile(String fileName) {
            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                processFile(reader);
            } catch (IOException e) {
                logger.error("Error while reading from file {}", fileName);
                logger.error("{}", e);
            }
            return this;
        }

        private void processFile(BufferedReader reader) throws IOException {
            String line = "";
            while ((line = reader.readLine()) != null) {
                int shipCount = Integer.parseInt(line);
                ShipShape shipShape = getValues(reader);
                addShips(shipCount, shipShape);
            }
        }

        /**
         * The only method through which the torpedo can be got.
         *
         * @return the builded torpedo
         */
        public CleverTorpedo build() {
            return torpedo;
        }

        private Ship getPlaceableShip(ShipShape shape) {
            Ship ship;
            Random random = new Random();
            do {
                int offsetX = random.nextInt(width - shape.getWidth());
                int offsetY = random.nextInt(height - shape.getHeight());
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

        private ShipShape getValues(BufferedReader reader) throws IOException {
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
            return new ShipShape(values);
        }

        private void addShips(int shipCount, ShipShape shape) {
            for (int counter = 0; counter < shipCount; counter++) {
                addShip(shape);
            }
        }
    }

}
