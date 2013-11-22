package com.epam.livingpope.torpedo.shapes;

import java.util.ArrayList;
import java.util.Collections;

public class Ship implements Hittable {

    private final ArrayList<Field> shipFieldList = new ArrayList<>();
    private final ShipShape shape;

    public Ship(ShipShape shape, int offsetX, int offsetY) {
        this.shape = shape;
        createShipFieldList(shape, offsetX, offsetY);
    }

    private void createShipFieldList(ShipShape shape, int offsetX, int offsetY) {
        Field[][] fields = shape.fields;
        for(int rowIndex=0;rowIndex<fields[0].length;rowIndex++) {
            for(int colIndex=0;colIndex<fields.length;colIndex++) {
                if(FieldState.UNHIT_SHIP.equals(fields[rowIndex][colIndex])) {
                    shipFieldList.add(new Field(new Point(rowIndex + offsetY, colIndex + offsetX), FieldState.UNHIT_SHIP));
                }
            }
        }
    }

    @Override
    public boolean isHittable(Point point) {
        boolean result = false;
        for (Field field : shipFieldList) {
            if(field.isHittable(point)) {
                result = true;
                break;
            }
        }
        return result;
    }

    @Override
    public void hit(Point point) {
        for (Field field : shipFieldList) {
            if(field.isHittable(point)) {
                field.hit(point);
                break;
            }
        }
    }

    @Override
    public boolean isUnHit() {
        boolean result = false;
        for (Field field : shipFieldList) {
            if(!field.isUnHit()) {
                result = false;
                break;
            }
        }
        return result;
    }

    public boolean isSunk() {
        boolean result = false;
        for (Field field : shipFieldList) {
            if(field.isUnHit()) {
                result = false;
                break;
            }
        }
        return result;
    }

    public boolean hasConflictWithShip(Ship ship) {
        return !Collections.disjoint(shipFieldList, ship.shipFieldList);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((shipFieldList == null) ? 0 : shipFieldList.hashCode());
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
        if (shipFieldList == null) {
            if (other.shipFieldList != null)
                return false;
        } else if (!shipFieldList.equals(other.shipFieldList))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Ship [pointList=" + shipFieldList + "]";
    }

}
