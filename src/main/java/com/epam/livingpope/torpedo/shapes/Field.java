package com.epam.livingpope.torpedo.shapes;

import static com.epam.livingpope.torpedo.shapes.FieldState.HIT_SHIP;
import static com.epam.livingpope.torpedo.shapes.FieldState.UNHIT_SHIP;

public class Field implements Hittable {

    private final Point point;
    private FieldState state;
    
    public Field(Point point, FieldState state) {
        this.point = point;
        this.state = state;
    }
    
    public boolean isHittable(Point point) {
        return this.point.equals(point) && state.equals(UNHIT_SHIP);
    }
    
    public void hit(Point point) {
        if(!isHittable(point)) {
            throw new IllegalArgumentException("Cannot hit ship on field with state " + state);
        }
        state = HIT_SHIP;
    }
}
