package com.epam.livingpope.torpedo.shapes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.epam.livingpope.torpedo.targeting.RandomTargetingSystem;

public class ShipBuilder {

    RandomTargetingSystem targetingSystem;

    public ShipBuilder(RandomTargetingSystem targetingSystem) {
        this.targetingSystem = targetingSystem;
    }

    public ArrayList<Ship> shipsFromFile(String fileName) {
        BufferedReader reader;
        ArrayList<Ship> ships = new ArrayList<Ship>();

        try {
            reader = new BufferedReader(new FileReader(fileName));
            String line = "";
            try {
                while ((line = reader.readLine()) != null) {
                    // System.out.println("line: " + line);
                    int shipCount = Integer.parseInt(line);
                    boolean[][] shape = createShape(reader);
                    for (int i = 0; i < shipCount; i++) {
                        Ship generateShip = targetingSystem.generateShip(shape);
                        while (hasConflictWithOthers(generateShip, ships)) {
                            generateShip = targetingSystem.generateShip(shape);
                        }
                        System.out.println(generateShip);
                        ships.add(generateShip);
                    }

                }
            } catch (IOException e) {
                System.out.println(e);
            }

        } catch (IOException e1) {
            System.out.println(e1);
        }
        return ships;
    }

    private boolean[][] createShape(BufferedReader reader) throws IOException {
        String shipLine;
        boolean[][] shipFileShape = new boolean[4][4];
        for (int j = 0; j < 4; j++) {
            shipLine = reader.readLine();
            // System.out.println("shipline: " + shipLine);
            for (int k = 0; k < 4; k++) {
                if (shipLine.charAt(k) == 'x' || shipLine.charAt(k) == 'X') {
                    shipFileShape[j][k] = true;
                } else {
                    shipFileShape[j][k] = false;
                }
            }
        }
        return shipFileShape;
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

}
