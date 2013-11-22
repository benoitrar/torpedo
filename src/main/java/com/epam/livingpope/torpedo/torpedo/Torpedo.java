package com.epam.livingpope.torpedo.torpedo;

import com.epam.livingpope.torpedo.communication.BulletStatus;
import com.epam.livingpope.torpedo.shapes.Point;

public interface Torpedo {
    BulletStatus fire(Point target);

    public char[][] getTableWithShips();

    // void printAllShips(int tableWidth, int tableHeight);

    boolean isGameOver();
}
