package com.epam.livingpope.torpedo.shapes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class for...
 *
 * @author Livia_Erdelyi Benedek_Kiss
 */

public class Ship implements Hittable {

    private final List<Point> shipPointList = new ArrayList<>();
    private final ShipShape shape;

    /**
     * Creates a ship with the given shape and offset.
     *
     * @param shape
     *            the shape of the ship
     * @param offsetX
     *            offset to horizontal direction
     * @param offsetY
     *            offset to vertical direction
     */
    public Ship(ShipShape shape, int offsetX, int offsetY) {
        this.shape = shape;
        createShipPointList(shape, offsetX, offsetY);
    }

    private void createShipPointList(ShipShape shape, int offsetX, int offsetY) {
        FieldState[][] fields = shape.getFields();
        for (int rowIndex = 0; rowIndex < fields.length; rowIndex++) {
            for (int colIndex = 0; colIndex < fields[0].length; colIndex++) {
                if (FieldState.UNHIT_SHIP.equals(fields[rowIndex][colIndex])) {
                    shipPointList.add(new Point(rowIndex + offsetY, colIndex + offsetX));
                }
            }
        }
    }

    @Override
    public boolean isHittable(Point point) {
        return shipPointList.contains(point);
    }

    @Override
    public void hit(Point point) {
        if (!shipPointList.contains(point)) {
            throw new UnsupportedOperationException("This point cannot be hit " + point);
        }
        shipPointList.remove(point);
    }

    public boolean isSunk() {
        return shipPointList.isEmpty();
    }

    /**
     * Tells if two ships share the same point.
     *
     * @param ship
     * @return
     */
    public boolean hasConflictWithShip(Ship ship) {
        return !Collections.disjoint(shipPointList, ship.shipPointList);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (shipPointList == null ? 0 : shipPointList.hashCode());
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
        Ship other = (Ship) obj;
        if (shipPointList == null) {
            if (other.shipPointList != null) {
                return false;
            }
        } else if (!shipPointList.equals(other.shipPointList)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Ship [pointList=" + shipPointList + "]";
    }

    public List<Point> getShipPointList() {
        return shipPointList;
    }

    public int getWidth() {
        return shape.getWidth();
    }

    public int getHeight() {
        return shape.getHeight();
    }

}
