package com.epam.livingpope.torpedo.shapes;

import java.util.ArrayList;
import java.util.Collections;

public class Ship {

    private static final String SHIP_DOES_NOT_CONTAIN_POINT = "Ship does not contain point: ";
    private final ArrayList<Point> pointList;

    public Ship(ArrayList<Point> pointList) {
        this.pointList = pointList;
    }

    // TODO
    // could be removed with some effort
    public ArrayList<Point> getPointList() {
        return pointList;
    }

    public boolean hasPoint(Point point) {
        return pointList.contains(point);
    }

    public void hitPoint(Point target) {
        if (!hasPoint(target)) {
            throw new IllegalArgumentException(SHIP_DOES_NOT_CONTAIN_POINT + target);
        }
        handleHit(target);
    }

    private void handleHit(Point point) {
        pointList.remove(point);
    }

    public boolean isSunk() {
        return pointList.isEmpty();
    }

    public boolean hasConflictWithShip(Ship ship) {
        return !Collections.disjoint(pointList, ship.pointList);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((pointList == null) ? 0 : pointList.hashCode());
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
        if (pointList == null) {
            if (other.pointList != null)
                return false;
        } else if (!pointList.equals(other.pointList))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Ship [pointList=" + pointList + "]";
    }

}
