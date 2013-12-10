package com.epam.livingpope.torpedo.shapes;

/**
 * Class for...
 *
 * @author Livia_Erdelyi Benedek_Kiss
 */

public class Point {

    public static final char EMPTY_POINT = ' ';
    public static final char MISSED_POINT = '.';
    public static final char SHIP_POINT = 'O';
    public static final char HIT_SHIP_POINT = '#';

    private final int x;
    private final int y;

    /**
     * Creates point with given coordinates.
     *
     * @param x
     *            x coordinate of the point
     * @param y
     *            y coordinate of the point
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Gives the result of an addition, when points are handled as vectors.
     *
     * @param otherPoint
     *            to be added
     * @return the result of the addition
     */
    public Point addPoint(Point otherPoint) {
        return new Point(x + otherPoint.x, y + otherPoint.y);
    }

    @Override
    public String toString() {
        return "Point [x=" + x + ", y=" + y + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + x;
        result = prime * result + y;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Point other = (Point) obj;
        if (x != other.x) {
            return false;
        }
        if (y != other.y) {
            return false;
        }
        return true;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
