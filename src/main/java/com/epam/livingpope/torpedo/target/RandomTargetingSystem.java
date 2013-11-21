package com.epam.livingpope.torpedo.target;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.epam.livingpope.torpedo.shapes.Point;
import com.epam.livingpope.torpedo.shapes.Ship;
import com.epam.livingpope.torpedo.shapes.ShipShape;
import com.epam.livingpope.torpedo.shapes.Table;

public class RandomTargetingSystem implements TargetingSystem {
    public static final int SHIPMAXLENGTH = 4;

    private static final Point NORTH = new Point(0, 1);
    private static final Point EAST = new Point(1, 0);
    private static final Point SOUTH = new Point(0, -1);
    private static final Point WEST = new Point(-1, 0);
    private static final Point DEFAULT_DIRECTION = EAST;

    private final Random random = new Random();
    private Table table;
    private Point lastTarget;
    private Point hitPoint;
    private List<Point> firedPoints = new ArrayList<>();
    private List<Point> potentialShipPoints = new ArrayList<>();
    private List<Point> suspiciousPoints = new ArrayList<>();
    private Point direction = DEFAULT_DIRECTION;

    public RandomTargetingSystem(Table table) {
        this.table = table;
    }

    public Ship generateShip() {
        boolean[][] shape;
        if (random.nextBoolean()) {
            shape = ShipShape.getShipForLength(random.nextInt(SHIPMAXLENGTH) + 1);
        } else {
            shape = ShipShape.getSpecialShip();
        }
        Point startPoint = createStartPointForShip(shape[0].length, shape.length);
        ArrayList<Point> pointList = pointListFromShape(startPoint, shape);

        return new Ship(pointList);
    }

    public Ship generateShip(boolean[][] shape) {
        Point startPoint = createStartPointForShip(shape[0].length, shape.length);
        ArrayList<Point> pointList = pointListFromShape(startPoint, shape);

        return new Ship(pointList);
    }

    private Point createStartPointForShip(int shipWidth, int shipHeight) {
        Point point = generatePoint();
        // TODO check coordinates
        while (!table.isOnTable(point.x + shipWidth, point.y + shipHeight)) {
            point = generatePoint();
        }
        return point;
    }

    private ArrayList<Point> pointListFromShape(Point point, boolean[][] shape) {
        ArrayList<Point> pointList = new ArrayList<Point>();
        for (int rowIndex = 0; rowIndex < shape.length; rowIndex++) {
            for (int colIndex = 0; colIndex < shape[rowIndex].length; colIndex++) {
                if (shape[rowIndex][colIndex]) {
                    pointList.add(new Point(point.x + rowIndex, point.y + colIndex));
                }
            }
        }
        return pointList;
    }

    public Point generatePoint() {
        return generatePoint(table.getWidth(), table.getHeight());
    }

    public Point generatePoint(int width, int height) {
        Point point = new Point(random.nextInt(width), random.nextInt(height));
        return point;
    }

    public Point nextTarget() {
        lastTarget = calculateNextTarget();
        firedPoints.add(lastTarget);
        // System.err.println(lastTarget);
        return lastTarget;
    }

    private Point calculateNextTarget() {
        Point nextTarget;

        if (!potentialShipPoints.isEmpty()) {
            nextTarget = potentialShipPoints.remove(0);
        } else if (!suspiciousPoints.isEmpty()) {
            nextTarget = suspiciousPoints.remove(0);
        } else {
            nextTarget = generatePoint();
            while (firedPoints.contains(nextTarget)) {
                nextTarget = generatePoint();
            }
        }

        return nextTarget;
    }

    public void onHit() {
        if (hitPoint == null) {
            hitPoint = lastTarget;
            potentialShipPoints = getPotentialShipPoints(hitPoint);
            suspiciousPoints.addAll(potentialShipPoints);
        } else {
            potentialShipPoints.addAll(getPotentialShipPoints(lastTarget));
        }
    }

    public void onMiss() {
        if (hitPoint != null) {
            direction = rotateDirection(direction);
        }
    }

    private Point rotateDirection(Point direction) {
        if (direction.equals(NORTH)) {
            return EAST;
        } else if (direction.equals(EAST)) {
            return SOUTH;
        } else if (direction.equals(SOUTH)) {
            return WEST;
        } else {
            return NORTH;
        }
    }

    public void onSunk() {
        hitPoint = null;
        potentialShipPoints.clear();
        direction = DEFAULT_DIRECTION;
    }

    private List<Point> getPotentialShipPoints(Point shipPoint) {
        List<Point> resultList = new ArrayList<>();

        Point point = shipPoint.addPoint(NORTH);
        if (table.isOnTable(point)) {
            resultList.add(point);
        }
        point = shipPoint.addPoint(EAST);
        if (table.isOnTable(point)) {
            resultList.add(point);
        }
        point = shipPoint.addPoint(SOUTH);
        if (table.isOnTable(point)) {
            resultList.add(point);
        }
        point = shipPoint.addPoint(WEST);
        if (table.isOnTable(point)) {
            resultList.add(point);
        }

        return resultList;
    }

    public Point firstTarget() {
        lastTarget = new Point(table.getWidth() / 2, table.getHeight() / 2);
        return lastTarget;
    }

    public int getTableWidth() {
        return table.getWidth();
    }

    public int getTableHeight() {
        return table.getHeight();
    }
}
