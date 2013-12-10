package com.epam.livingpope.torpedo.shapes;

/**
 * Class for...
 *
 * @author Livia_Erdelyi Benedek_Kiss
 */

public interface Hittable {

    /**
     * Tells if given point is an unhit ship.
     *
     * @return true if point is part of an unhit ship
     * @param target
     *            target of a possible hit
     */
    boolean isHittable(Point target);

    /**
     * Fire to a point.
     *
     * @param target
     *            target of the hit
     */
    void hit(Point target);
}
