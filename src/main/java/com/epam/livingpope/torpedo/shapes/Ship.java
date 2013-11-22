package com.epam.livingpope.torpedo.shapes;

import java.util.ArrayList;
import java.util.Collections;

public class Ship implements Hittable {

    private final ArrayList<Point> shipPointList = new ArrayList<>();
    private final ShipShape shape;
    private final int offsetX;
    private final int offsetY;

    public Ship(ShipShape shape, int offsetX, int offsetY) {
        this.shape = shape;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        createShipFieldList(shape, offsetX, offsetY);
    }

    private void createShipFieldList(ShipShape shape, int offsetX, int offsetY) {
        FieldState[][] fields = shape.fields;
        for(int rowIndex=0;rowIndex<fields[0].length;rowIndex++) {
            for(int colIndex=0;colIndex<fields.length;colIndex++) {
                if(FieldState.UNHIT_SHIP.equals(fields[rowIndex][colIndex])) {
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
        if(!shipPointList.contains(point)) {
            throw new UnsupportedOperationException("This point cannot be hit " + point);
        }
        shipPointList.remove(point);
    }

    public boolean isSunk() {
        return shipPointList.isEmpty();
    }

    public boolean hasConflictWithShip(Ship ship) {
        return !Collections.disjoint(shipPointList, ship.shipPointList);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((shipPointList == null) ? 0 : shipPointList.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Ship other = (Ship) obj;
        if (shipPointList == null) {
            if (other.shipPointList != null)
                return false;
        } else if (!shipPointList.equals(other.shipPointList))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Ship [pointList=" + shipPointList + "]";
    }

}
