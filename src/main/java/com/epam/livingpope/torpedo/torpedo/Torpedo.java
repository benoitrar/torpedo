package com.epam.livingpope.torpedo.torpedo;

import com.epam.livingpope.torpedo.communication.Status;
import com.epam.livingpope.torpedo.shapes.Point;

public interface Torpedo {
    Status fire(Point target);

    public char[][] getTableWithShips();

    // void printAllShips(int tableWidth, int tableHeight);

    boolean isGameOver();
}
