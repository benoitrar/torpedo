package com.epam.livingpope.torpedo.shapes;
import java.util.Arrays;

public class ShipShape {

    public static boolean[][] getShipForLength(int length) {
        boolean array[] = new boolean[length];
        Arrays.fill(array, true);
        return new boolean[][] { array };
    }

    public static boolean[][] getSpecialShip() {
        return new boolean[][] { { false, true, false }, { true, true, true } };
    }

    public static boolean[][] getShipFromFile() {

        return new boolean[][] { {} };
    }
}
