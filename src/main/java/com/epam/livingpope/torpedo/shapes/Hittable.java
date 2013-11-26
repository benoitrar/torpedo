package com.epam.livingpope.torpedo.shapes;

public interface Hittable {

    /**
     * Tells if given point is an unhit ship.
     * 
     * @return true if point is part of an unhit ship
     */
    boolean isHittable(Point point);
    
    void hit(Point point);
}
