package com.epam.livingpope.torpedo.shapes;

/**
 * Class for...
 *
 * @author Livia_Erdelyi Benedek_Kiss
 */

public class GameBoard implements Hittable {
    private final int boardWidth;
    private final int boardHeight;
    private final FieldState[][] board;

    /**
     * Creates board with given bounds and fills it with the initial state.
     *
     * @param tableWidth
     *            width of the board
     * @param tableHeight
     *            height of the board
     * @param initialState
     *            initial state to fill the board with
     */
    public GameBoard(int tableWidth, int tableHeight, FieldState initialState) {
        boardWidth = tableWidth;
        boardHeight = tableHeight;

        board = new FieldState[tableWidth][tableHeight];
        for (int i = 0; i < tableHeight; i++) {
            for (int j = 0; j < tableWidth; j++) {
                board[i][j] = initialState;
            }
        }
    }

    /**
     * Check if a given point is on the board.
     *
     * @param point
     *            to be checked
     * @return true if point is on board
     */
    public boolean isOnBoard(Point point) {
        return point.getX() >= 0 && point.getX() < boardHeight && point.getY() >= 0 && point.getY() < boardWidth;
    }

    @Override
    public boolean isHittable(Point point) {
        boolean result = true;
        if (!isOnBoard(point)) {
            result = false;
        } else if (!board[point.getX()][point.getY()].isUnhitShip()) {
            result = false;
        }
        return result;
    }

    @Override
    public void hit(Point point) {
        if (isOnBoard(point)) {
            board[point.getX()][point.getY()] = board[point.getX()][point.getY()].getStateOnHit();
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

    /**
     * Adds a new ship to the board.
     *
     * @param ship
     *            to be added
     */
    public void add(Ship ship) {
        for (Point point : ship.getShipPointList()) {
            board[point.getX()][point.getY()] = FieldState.UNHIT_SHIP;
        }
    }

    public int getBoardWidth() {
        return boardWidth;
    }

    public int getBoardHeight() {
        return boardHeight;
    }

}
