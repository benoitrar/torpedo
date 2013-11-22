package com.epam.livingpope.torpedo.torpedo;

import com.epam.livingpope.torpedo.communication.GameStatus;
import com.epam.livingpope.torpedo.shapes.Point;

public interface Torpedo {
    GameStatus fire(Point target);
}
