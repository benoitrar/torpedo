package com.epam.livingpope.torpedo.shapes;

import java.util.Arrays;

public class ShipShape {

    public final FieldState[][] fields;

    public ShipShape(FieldState[][] fields) {
        FieldState[][] cutFields = cutFields(fields);
        this.fields = cutFields;
    }

    private FieldState[][] cutFields(FieldState[][] fields) {
        return cutHorizontally(fields);
    }

    private FieldState[][] cutHorizontally(FieldState[][] fields) {
        FieldState[][] result = fields;

        int rowNum = fields.length;
        int colNum = fields[0].length;
        boolean rowsToDelete[] = new boolean[rowNum];
        Arrays.fill(rowsToDelete, false);
        int numberOfRowsToDelete = 0;

        for (int rowIndex = 0; rowIndex < fields.length; rowIndex++) {
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
            int newRowNum = fields[0].length - numberOfRowsToDelete;
            if (newRowNum == 0) {
                throw new IllegalArgumentException("Shape must not be empty");
            }
            result = new FieldState[newRowNum][colNum];
            int actRow = 0;
            for (int rowIndex = 0; rowIndex < rowsToDelete.length; rowIndex++) {
                if (!rowsToDelete[rowIndex]) {
                    for (FieldState[] fieldStates : fields) {
                        result[actRow] = fieldStates;
                    }
                    actRow++;
                }
            }
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
