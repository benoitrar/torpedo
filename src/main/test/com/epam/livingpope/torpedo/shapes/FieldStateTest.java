package com.epam.livingpope.torpedo.shapes;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.junit.Test;

public class FieldStateTest {

    private FieldState underTest;

    @Test
    public void testGetStateOnHitWhenUnhitEmpty() {
        // GIVEN
        underTest = FieldState.UNHIT_EMPTY;
        FieldState expectedResult = FieldState.HIT_EMPTY;
        // WHEN
        FieldState result = underTest.getStateOnHit();
        // THEN
        Assert.assertEquals(expectedResult, result);
    }

    @Test
    public void testGetStateOnHitWhenUnhitShip() {
        // GIVEN
        underTest = FieldState.UNHIT_SHIP;
        FieldState expectedResult = FieldState.HIT_SHIP;
        // WHEN
        FieldState result = underTest.getStateOnHit();
        // THEN
        Assert.assertEquals(expectedResult, result);
    }

    @Test
    public void testGetStateOnHitWhenHitEmpty() {
        // GIVEN
        underTest = FieldState.HIT_EMPTY;
        FieldState expectedResult = FieldState.HIT_EMPTY;
        // WHEN
        FieldState result = underTest.getStateOnHit();
        // THEN
        Assert.assertEquals(expectedResult, result);
    }

    @Test
    public void testGetStateOnHitWhenHitShip() {
        // GIVEN
        underTest = FieldState.HIT_SHIP;
        FieldState expectedResult = FieldState.HIT_SHIP;
        // WHEN
        FieldState result = underTest.getStateOnHit();
        // THEN
        Assert.assertEquals(expectedResult, result);
    }

    @Test
    public void testIsUnhitShipWhenUnhitShip() {
        // GIVEN
        underTest = FieldState.UNHIT_SHIP;
        // WHEN
        boolean result = underTest.isUnhitShip();
        // THEN
        assertTrue(result);
    }

    @Test
    public void testIsUnhitShipWhenNotUnhitShip() {
        // GIVEN
        underTest = FieldState.UNHIT_EMPTY;
        // WHEN
        boolean result = underTest.isUnhitShip();
        // THEN
        assertFalse(result);
    }

}