package com.epam.livingpope.torpedo.shapes;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Class for...
 *
 * @author Livia_Erdelyi Benedek_Kiss
 */

public class PointTest {

    private Point underTest;

    @Before
    public void setup() {
        underTest = new Point(1, 1);
    }

    @Test
    public void testAddPoint() {
        // GIVEN
        Point pointToAdd = new Point(2, 2);
        Point expectedResult = new Point(3, 3);
        // WHEN
        Point result = underTest.addPoint(pointToAdd);
        // THEN
        Assert.assertEquals(expectedResult, result);
    }
}
