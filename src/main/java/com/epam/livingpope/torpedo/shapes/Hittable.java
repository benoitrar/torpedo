package com.epam.livingpope.torpedo.shapes;

public interface Hittable {

    boolean isHittable(Point point);
    
    void hit(Point point);
}
