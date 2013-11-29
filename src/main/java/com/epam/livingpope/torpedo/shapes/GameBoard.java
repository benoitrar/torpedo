package com.epam.livingpope.torpedo.shapes;

public class GameBoard implements Hittable {
    private FieldState[][] board;
    public final int boardWidth;
    public final int boardHeight;

    public GameBoard(int tableWidth, int tableHeight, FieldState initialState) {
        this.boardWidth = tableWidth;
        this.boardHeight = tableHeight;

        board = new FieldState[tableWidth][tableHeight];
        for (int i = 0; i < tableHeight; i++) {
            for (int j = 0; j < tableWidth; j++) {
                board[i][j] = initialState;
            }
        }
    }

    public boolean isOnBoard(Point point) {
        return point.x >= 0 && point.x < boardHeight && point.y >= 0 && point.y < boardWidth;
    }

    @Override
    public boolean isHittable(Point point) {
        boolean result = true;
        if (!isOnBoard(point)) {
            result = false;
        } else if (!board[point.x][point.y].isUnhitShip()) {
            result = false;
        }
        return result;
    }

    @Override
    public void hit(Point point) {
        if (isOnBoard(point)) {
            board[point.x][point.y] = board[point.x][point.y].getStateOnHit();
        }
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        printHorizontalLine(sb);
        for (int i = 0; i < boardHeight; i++) {
            sb.append("|");
            for (int j = 0; j < boardWidth; j++) {
                sb.append(board[i][j] + " ");
            }
            sb.append("|\n");
        }
        printHorizontalLine(sb);
        return sb.toString();
    }

    private void printHorizontalLine(StringBuffer sb) {
        for (int j = 0; j <= boardWidth; j++) {
            sb.append("--");
        }
        sb.append("\n");
    }

    public void add(Ship ship) {
        for (Point point : ship.getShipPointList()) {
            board[point.x][point.y] = FieldState.UNHIT_SHIP;
        }
    }

}
