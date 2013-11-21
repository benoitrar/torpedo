package com.epam.livingpope.torpedo.target;

import com.epam.livingpope.torpedo.shapes.Point;
import com.epam.livingpope.torpedo.shapes.Ship;

public interface TargetingSystem {
    public Point generatePoint();

    public Ship generateShip();
}
