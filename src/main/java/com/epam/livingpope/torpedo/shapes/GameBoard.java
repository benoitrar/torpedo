package com.epam.livingpope.torpedo.shapes;


public class Table {
    private int width;
    private int height;

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Table(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public boolean isOnTable(Point point) {
        return isOnTable(point.x, point.y);
    }

    public boolean isOnTable(int x, int y) {
        boolean result = false;
        if (width > x && x >= 0 && height > y && y >= 0) {
            result = true;
        }
        return result;
    }
}
