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

public class GameBoardTest {

    private GameBoard underTest;

    @Before
    public void setup() {
        underTest = new GameBoard(20, 20, FieldState.UNHIT_SHIP);
    }

    @Test
    public void testIsOnBoardWhenPointIsNotOnBoard() {
        // GIVEN
        Point notOnBoard1 = new Point(-1, 0);
        Point notOnBoard2 = new Point(0, -1);
        Point notOnBoard3 = new Point(20, 0);
        Point notOnBoard4 = new Point(0, 20);
        // WHEN
        // THEN
        assertFalse(underTest.isOnBoard(notOnBoard1));
        assertFalse(underTest.isOnBoard(notOnBoard2));
        assertFalse(underTest.isOnBoard(notOnBoard3));
        assertFalse(underTest.isOnBoard(notOnBoard4));
    }

    @Test
    public void testIsHittableWithUnhitShipOnTable() {
        // GIVEN
        Point onTable = new Point(10, 10);
        // WHEN
        boolean result = underTest.isHittable(onTable);
        // THEN
        assertTrue(result);
    }

    @Test
    public void testIsHittableWithPointNotOnTable() {
        // GIVEN
        Point notOnTable = new Point(-10, -10);
        // WHEN
        boolean result = underTest.isHittable(notOnTable);
        // THEN
        assertFalse(result);
    }

    @Test
    public void testHit() {
        // GIVEN
        Point onTable = new Point(10, 10);
        Point otherPointOnTable = new Point(0, 0);
        // WHEN
        underTest.hit(onTable);
        // THEN
        assertFalse(underTest.isHittable(onTable));
        assertTrue(underTest.isHittable(otherPointOnTable));
    }

    @Test
    public void testAddShip() {
        // GIVEN
        underTest = new GameBoard(20, 20, FieldState.UNHIT_EMPTY);
        Point shipPoint = new Point(10, 10);
        Ship ship = new Ship(new ShipShape(new FieldState[][]{{FieldState.UNHIT_SHIP}}), 10, 10);
        Point otherPointOnTable = new Point(0, 0);
        // WHEN
        underTest.add(ship);
        // THEN
        assertTrue(underTest.isHittable(shipPoint));
        assertFalse(underTest.isHittable(otherPointOnTable));
    }
}
