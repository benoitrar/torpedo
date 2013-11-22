package com.epam.livingpope.torpedo.shapes;

import static com.epam.livingpope.torpedo.shapes.FieldState.EMPTY;
import static com.epam.livingpope.torpedo.shapes.FieldState.HIT_SHIP;
import static com.epam.livingpope.torpedo.shapes.FieldState.UNHIT_SHIP;
import static com.epam.livingpope.torpedo.shapes.FieldState.UNSPECIFIED;

public class Field {

    private final Point point;
    private FieldState state;
    
    private Field(Point point, FieldState state) {
        this.point = point;
        this.state = state;
    }
    
    public static Field getEmptyField(Point point) {
        return new Field(point, EMPTY);
    }
    
    public static Field getShipField(Point point) {
        return new Field(point, UNHIT_SHIP);
    }
    
    public static Field getUnspecifiedField(Point point) {
        return new Field(point, UNSPECIFIED);
    }
    
    public boolean isHittable() {
        return state.equals(UNHIT_SHIP);
    }
    
    public void hit() {
        if(!isHittable()) {
            throw new IllegalArgumentException("Cannot hit ship on field with state " + state);
        }
        state = HIT_SHIP;
    }
    
    public int getX() {
        return point.x;
    }
    
    public int getY() {
        return point.y;
    }
}
