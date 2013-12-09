package com.epam.livingpope.torpedo.shapes;

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

    @Test(expected = UnsupportedOperationException.class)
    public void testGetStateOnHitWhenUnspecified() {
        // GIVEN
        underTest = FieldState.UNSPECIFIED;
        // WHEN
        underTest.getStateOnHit();
        // THEN -> UnsupportedOperationException is thrown as UNSPECIFIED field
        // cannot be hit
    }

}