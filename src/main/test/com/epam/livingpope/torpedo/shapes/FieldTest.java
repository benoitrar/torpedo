package com.epam.livingpope.torpedo.shapes;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * Class for...
 *
 * @author Livia_Erdelyi Benedek_Kiss
 */

public class FieldTest {

    private Field underTest;
    private Point point;

    @Before
    public void setup() {
        point = new Point(2, 2);
    }

    @Test
    public void testIsHittableWhenUnhitShipAndTheSamePoint() {
        // GIVEN
        underTest = new Field(point, FieldState.UNHIT_SHIP);
        // WHEN
        boolean result = underTest.isHittable(point);
        // THEN
        assertTrue(result);
    }

    @Test
    public void testIsHittableWhenUnhitShipAndNotTheSamePoint() {
        // GIVEN
        underTest = new Field(point, FieldState.UNHIT_SHIP);
        Point notTheSamePoint = new Point(3, 3);
        // WHEN
        boolean result = underTest.isHittable(notTheSamePoint);
        // THEN
        assertFalse(result);
    }

    @Test
    public void testIsHittableWhenNotUnhitShipAndTheSamePoint() {
        // GIVEN
        underTest = new Field(point, FieldState.UNHIT_EMPTY);
        // WHEN
        boolean result = underTest.isHittable(point);
        // THEN
        assertFalse(result);
    }

    @Test
    public void testHitWhenUnhitShipAndTheSamePoint() {
        // GIVEN
        underTest = new Field(point, FieldState.UNHIT_SHIP);
        // WHEN
        underTest.hit(point);
        // THEN
        // no exceptions
        assertFalse(underTest.isHittable(point));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testHitWhenHitShipAndTheSamePoint() {
        // GIVEN
        underTest = new Field(point, FieldState.HIT_SHIP);
        // WHEN
        underTest.hit(point);
        // THEN
        // IllegalArgumentException as field is hit already
    }
}
