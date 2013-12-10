package com.epam.livingpope.torpedo.shapes;

import static com.epam.livingpope.torpedo.shapes.FieldState.HIT_SHIP;
import static com.epam.livingpope.torpedo.shapes.FieldState.UNHIT_SHIP;

/**
 * Class for...
 *
 * @author Livia_Erdelyi Benedek_Kiss
 */

public class Field implements Hittable {

    private final Point point;
    private FieldState state;

    /**
     * Field has two dependencies: a point and a state.
     *
     * @param point
     *            the point where this field is placed.
     * @param state
     *            the initial state of the field
     */
    public Field(Point point, FieldState state) {
        this.point = point;
        this.state = state;
    }

    @Override
    public boolean isHittable(Point point) {
        return this.point.equals(point) && state.equals(UNHIT_SHIP);
    }

    @Override
    public void hit(Point point) {
        if (!isHittable(point)) {
            throw new IllegalArgumentException("Cannot hit ship on field with state " + state);
        }
        state = HIT_SHIP;
    }
}
