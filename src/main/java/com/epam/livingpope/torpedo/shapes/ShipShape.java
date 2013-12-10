package com.epam.livingpope.torpedo.shapes;

import java.util.Arrays;

public class ShipShape {

    public final FieldState[][] fields;

    public ShipShape(FieldState[][] fields) {
        FieldState[][] cutFields = cutFields(fields);
        this.fields = cutFields;
    }

    private FieldState[][] cutFields(FieldState[][] fields) {
        FieldState[][] cutFields = cutHorizontally(fields);
        return cutVertically(cutFields);
    }

    private FieldState[][] cutHorizontally(FieldState[][] fields) {
        FieldState[][] result = null;

        int rowNum = fields.length;
        int colNum = fields[0].length;
        boolean rowsToDelete[] = new boolean[rowNum];
        Arrays.fill(rowsToDelete, false);
        int numberOfRowsToDelete = 0;

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
                numberOfRowsToDelete++;
            }
        }

        if (numberOfRowsToDelete != 0) {
            int newRowNum = rowNum - numberOfRowsToDelete;
            if (newRowNum == 0) {
                throw new IllegalArgumentException("Shape must not be empty");
            }
            result = new FieldState[newRowNum][colNum];
            int actRow = 0;
            for (int rowIndex = 0; rowIndex < rowNum; rowIndex++) {
                if (!rowsToDelete[rowIndex]) {
                    result[actRow] = fields[rowIndex];
                    actRow++;
                }
            }
        } else {
            return fields;
        }

        return result;
    }

    private FieldState[][] cutVertically(FieldState[][] fields) {
        FieldState[][] result = null;
        int rowNum = fields.length;
        int colNum = fields[0].length;

        boolean colsToDelete[] = new boolean[colNum];
        Arrays.fill(colsToDelete, false);
        int numberOfColsToDelete = 0;

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
                numberOfColsToDelete++;
            }

        }

        if (numberOfColsToDelete != 0) {
            int newColNum = colNum - numberOfColsToDelete;
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
            return fields;
        }
        return result;
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
}
