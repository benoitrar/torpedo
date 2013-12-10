package com.epam.livingpope.torpedo.torpedo;

import com.epam.livingpope.torpedo.communication.GameStatus;
import com.epam.livingpope.torpedo.shapes.Point;

/**
 * Class for...
 *
 * @author Livia_Erdelyi Benedek_Kiss
 */

public interface Torpedo {
    GameStatus getFireResult(Point target);
}
