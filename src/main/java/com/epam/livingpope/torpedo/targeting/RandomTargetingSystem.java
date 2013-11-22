package com.epam.livingpope.torpedo.targeting;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.epam.livingpope.torpedo.shapes.GameBoard;
import com.epam.livingpope.torpedo.shapes.Point;

public class RandomTargetingSystem implements TargetingSystem {
    public static final int SHIPMAXLENGTH = 4;

    private static final Point NORTH = new Point(0, 1);
    private static final Point EAST = new Point(1, 0);
    private static final Point SOUTH = new Point(0, -1);
    private static final Point WEST = new Point(-1, 0);
    private static final Point DEFAULT_DIRECTION = EAST;

    private final Random random = new Random();
    private GameBoard table;
    private Point lastTarget;
    private Point hitPoint;
    private List<Point> firedPoints = new ArrayList<>();
    private List<Point> potentialShipPoints = new ArrayList<>();
    private List<Point> suspiciousPoints = new ArrayList<>();
    private Point direction = DEFAULT_DIRECTION;

    public RandomTargetingSystem(GameBoard table) {
        this.table = table;
    }

    public Point generatePoint() {
        return generatePoint(table.width, table.height);
    }

    public Point generatePoint(int width, int height) {
        Point point = new Point(random.nextInt(width), random.nextInt(height));
        return point;
    }

    public Point nextTarget() {
        lastTarget = calculateNextTarget();
        firedPoints.add(lastTarget);
        return lastTarget;
    }

    private Point calculateNextTarget() {
        Point nextTarget = nextTargetTip();

        while (firedPoints.contains(nextTarget)) {
            nextTarget = nextTargetTip();
        }

        return nextTarget;
    }

    private Point nextTargetTip() {
        Point nextTarget;
        if (!potentialShipPoints.isEmpty()) {
            nextTarget = potentialShipPoints.remove(0);
        } else if (!suspiciousPoints.isEmpty()) {
            nextTarget = suspiciousPoints.remove(0);
        } else {
            nextTarget = generatePoint();
        }
        return nextTarget;
    }

    public void onHit() {
        if (hitPoint == null) {
            hitPoint = lastTarget;
            potentialShipPoints = getPotentialShipPoints(hitPoint);
            suspiciousPoints.addAll(potentialShipPoints);
        } else {
            List<Point> newPotentialShipPoints = getPotentialShipPoints(lastTarget);
            potentialShipPoints.addAll(newPotentialShipPoints);
            suspiciousPoints.addAll(newPotentialShipPoints);
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
        lastTarget = new Point(table.width / 2, table.height / 2);
        return lastTarget;
    }

    public int getTableWidth() {
        return table.width;
    }

    public int getTableHeight() {
        return table.height;
    }
}
