package com.epam.livingpope.torpedo.shapes;

import java.util.Arrays;

/**
 * Class for...
 *
 * @author Livia_Erdelyi Benedek_Kiss
 */

public class ShipShape {

    private final FieldState[][] fields;

    public ShipShape(FieldState[][] fields) {
        this.fields = cutFields(fields);
    }

    private FieldState[][] cutFields(FieldState[][] fields) {
        return cutHorizontally(cutVertically(fields));
    }

    private FieldState[][] cutHorizontally(FieldState[][] fields) {
        int rowNum = fields.length;
        int colNum = fields[0].length;

        boolean[] rowsToDelete = searchForRowsToDelete(fields, rowNum);
        int numberOfRowsToDelete = calculateNumOfArraysToDelete(rowsToDelete);

        return copySurvivingRows(fields, rowNum, colNum, rowsToDelete, numberOfRowsToDelete);
    }

    private int calculateNumOfArraysToDelete(boolean[] arraysToDelete) {
        int numberOfArraysToDelete = 0;
        for (boolean b : arraysToDelete) {
            if (b) {
                numberOfArraysToDelete++;
            }
        }
        return numberOfArraysToDelete;
    }

    private FieldState[][] copySurvivingRows(FieldState[][] fields, int rowNum, int colNum, boolean[] rowsToDelete, int numberOfRowsToDelete) {
        int newRowNum = rowNum - numberOfRowsToDelete;
        FieldState[][] result = new FieldState[newRowNum][colNum];
        if (numberOfRowsToDelete != 0) {
            if (newRowNum == 0) {
                throw new IllegalArgumentException("Shape must not be empty");
            }
            int actRow = 0;
            for (int rowIndex = 0; rowIndex < rowNum; rowIndex++) {
                if (!rowsToDelete[rowIndex]) {
                    result[actRow] = fields[rowIndex];
                    actRow++;
                }
            }
        } else {
            result = deepCopy(fields);
        }
        return result;
    }

    private FieldState[][] copySurvivingCols(FieldState[][] fields, int rowNum, int colNum, boolean[] colsToDelete) {
        int numberOfColsToDelete = calculateNumOfArraysToDelete(colsToDelete);
        int newColNum = colNum - numberOfColsToDelete;
        FieldState[][] result = new FieldState[rowNum][newColNum];
        if (numberOfColsToDelete != 0) {
            if (newColNum == 0) {
                throw new IllegalArgumentException("Shape must not be empty");
            }
            result = new FieldState[rowNum][newColNum];
            int actCol = 0;
            for (int rowIndex = 0; rowIndex < result.length; rowIndex++) {
                for (int colIndex = 0; colIndex < colNum; colIndex++) {
                    if (!colsToDelete[colIndex]) {
                        result[rowIndex][actCol] = fields[rowIndex][colIndex];
                        actCol++;
                    }
                }
                actCol = 0;
            }
        } else {
            result = deepCopy(fields);
        }
        return result;
    }

    private FieldState[][] deepCopy(FieldState[][] fields) {
        int rowNum = fields.length;
        int colNum = fields[0].length;
        FieldState[][] result = new FieldState[rowNum][colNum];

        for (int rowIndex = 0; rowIndex < rowNum; rowIndex++) {
            for (int colIndex = 0; colIndex < colNum; colIndex++) {
                result[rowIndex][colIndex] = fields[rowIndex][colIndex];
            }
        }

        return result;
    }

    private boolean[] searchForRowsToDelete(FieldState[][] fields, int rowNum) {
        boolean[] rowsToDelete = new boolean[rowNum];
        Arrays.fill(rowsToDelete, false);

        for (int rowIndex = 0; rowIndex < rowNum; rowIndex++) {
            boolean deleteRow = true;
            for (FieldState fieldState : fields[rowIndex]) {
                if (fieldState.isUnhitShip()) {
                    deleteRow = false;
                    break;
                }
            }
            if (deleteRow) {
                rowsToDelete[rowIndex] = true;
            }
        }
        return rowsToDelete;
    }

    private boolean[] searchForColsToDelete(FieldState[][] fields, int rowNum, int colNum) {
        boolean[] colsToDelete = new boolean[colNum];
        Arrays.fill(colsToDelete, false);

        for (int colIndex = 0; colIndex < colNum; colIndex++) {
            boolean deleteCol = true;
            for (int rowIndex = 0; rowIndex < rowNum; rowIndex++) {
                if (fields[rowIndex][colIndex].isUnhitShip()) {
                    deleteCol = false;
                    break;
                }
            }
            if (deleteCol) {
                colsToDelete[colIndex] = true;
            }

        }
        return colsToDelete;
    }

    private FieldState[][] cutVertically(FieldState[][] fields) {
        int rowNum = fields.length;
        int colNum = fields[0].length;

        boolean[] colsToDelete = searchForColsToDelete(fields, rowNum, colNum);

        return copySurvivingCols(fields, rowNum, colNum, colsToDelete);
    }

    public int getWidth(FieldState[][] fields) {
        return fields[0].length;
    }

    public int getHeight(FieldState[][] fields) {
        return fields.length;
    }

    public int getWidth() {
        return getWidth(fields);
    }

    public int getHeight() {
        return getHeight(fields);
    }

    // TODO
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (FieldState[] fieldStates : fields) {
            for (FieldState fieldState : fieldStates) {
                sb.append(fieldState.toString() + ' ');
            }
            sb.append('\n');
        }

        return sb.toString();
    }

    public FieldState[][] getFields() {
        return fields;
    }
}
