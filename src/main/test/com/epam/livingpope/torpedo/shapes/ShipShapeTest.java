package com.epam.livingpope.torpedo.shapes;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ShipShapeTest {

    private static final FieldState e = FieldState.UNHIT_EMPTY;
    private static final FieldState s = FieldState.UNHIT_SHIP;
    FieldState fields[][] = new FieldState[][] { { e, e, e, e }, { e, e, e, e }, { s, s, s, e }, { e, e, e, e } };

    @Test
    public void testConstructor() {
        // GIVEN
        ShipShape shape = new ShipShape(fields);
        int expectedHeight = 1;
        // WHEN
        int height = shape.getHeight();
        // THEN
        assertEquals(expectedHeight, height);
        System.out.println(shape);
    }
}
