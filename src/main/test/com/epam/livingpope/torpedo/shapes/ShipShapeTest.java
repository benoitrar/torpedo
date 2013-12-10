package com.epam.livingpope.torpedo.shapes;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class for...
 *
 * @author Livia_Erdelyi Benedek_Kiss
 */

public class ShipShapeTest {

    private static final FieldState E = FieldState.UNHIT_EMPTY;
    private static final FieldState S = FieldState.UNHIT_SHIP;
    // FieldState[][] fields = new FieldState[][] { { E, E, E, E }, { E, E, E, E
    // }, { S, S, S, E }, { E, E, E, E } };
    FieldState[][] fields = new FieldState[][]{{E, E, E, E}, {E, S, S, E}, {E, S, S, E}, {E, E, E, E}};
    // FieldState[][] fields = new FieldState[][] { { S, S, S, S }, { S, S, S, S
    // }, { E, S, S, S }, { S, S, S, S } };
    private final Logger logger = LoggerFactory.getLogger(ShipShapeTest.class);

    @Test
    public void testConstructor() {
        // GIVEN
        ShipShape shape = new ShipShape(fields);
        int expectedWidth = 2;
        int expectedHeight = 2;
        // WHEN
        int width = shape.getWidth();
        int height = shape.getHeight();
        // THEN
        logger.info("\n{}", shape);
        assertEquals(expectedWidth, width);
        assertEquals(expectedHeight, height);
    }
}
