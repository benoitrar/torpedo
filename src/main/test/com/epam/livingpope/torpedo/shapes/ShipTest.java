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

public class ShipTest {

    private Ship underTest;
    private Point onlyShipPoint = new Point(10, 10);

    @Before
    public void setup() {
        underTest = new Ship(new ShipShape(new FieldState[][]{{FieldState.UNHIT_SHIP}}), 10, 10);
    }

    @Test
    public void testIsHittableWithUnhitShipPoint() {
        // GIVEN in setup
        // WHEN
        boolean result = underTest.isHittable(onlyShipPoint);
        // THEN
        assertTrue(result);
    }

    @Test
    public void testIsHittableWithNonShipPoint() {
        // GIVEN
        Point nonShipPoint = new Point(-10, -10);
        // WHEN
        boolean result = underTest.isHittable(nonShipPoint);
        // THEN
        assertFalse(result);
    }

    @Test
    public void testHitWithUnhitShipPoint() {
        // GIVEN in setup
        // WHEN
        underTest.hit(onlyShipPoint);
        boolean result = underTest.isHittable(onlyShipPoint);
        // THEN
        assertFalse(result);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testHitWithHitShipPoint() {
        // GIVEN in setup
        // WHEN
        underTest.hit(onlyShipPoint);
        underTest.hit(onlyShipPoint);
        // THEN -> UnsupportedOperationException as point is already hit
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testHitWithNonShipPoint() {
        // GIVEN
        Point nonShipPoint = new Point(-10, -10);
        // WHEN
        underTest.hit(nonShipPoint);
        // THEN -> UnsupportedOperationException as point is already hit
    }

    @Test
    public void testIsSunkWhenNotSunk() {
        // GIVEN in setup
        // WHEN
        boolean result = underTest.isSunk();
        // THEN
        assertFalse(result);
    }

    @Test
    public void testIsSunkWhenSunk() {
        // GIVEN
        underTest.hit(onlyShipPoint);
        // WHEN
        boolean result = underTest.isSunk();
        // THEN
        assertTrue(result);
    }

    @Test
    public void testHasConflictWithShipWhenThereIsAConflict() {
        // GIVEN
        Ship otherShipWithConflict = new Ship(new ShipShape(new FieldState[][]{{FieldState.UNHIT_SHIP}}), 10, 10);
        // WHEN
        boolean result = underTest.hasConflictWithShip(otherShipWithConflict);
        // THEN
        assertTrue(result);
    }

    @Test
    public void testHasConflictWithShipWhenThereAreNoConflicts() {
        // GIVEN
        Ship otherShipWithConflict = new Ship(new ShipShape(new FieldState[][]{{FieldState.UNHIT_SHIP}}), 0, 0);
        // WHEN
        boolean result = underTest.hasConflictWithShip(otherShipWithConflict);
        // THEN
        assertFalse(result);
    }
}
