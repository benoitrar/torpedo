package com.epam.livingpope.torpedo.shapes;

public class ShipShape {

    public final FieldState[][] fields;

    public ShipShape(FieldState[][] fields) {
        FieldState[][] cutFields = cutFields(fields);
        this.fields = cutFields;
    }

    private FieldState[][] cutFields(FieldState[][] unCutFields) {
        FieldState[][] cutFields = cutHorizontally(fields);
        cutFields = cutVertically(fields);
        return cutFields;
    }

    private FieldState[][] cutHorizontally(FieldState[][] unCutFields) {
        for (int i = 0; i < unCutFields.length; i++) {
            if (isEmptyRow(unCutFields, i)) {
                unCutFields = deleteEmptyRow(unCutFields, i);
            }
        }
        return unCutFields;
    }

    private FieldState[][] deleteEmptyRow(FieldState[][] unCutFields, int rowNum) {
        FieldState[][] result = new FieldState[getWidth(unCutFields) - 1][getHeight(unCutFields)];
        // TODO
    }

    private boolean isEmptyRow(FieldState[][] unCutFields, int rowNum) {
        boolean result = true;
        for (int colNum = 0; colNum < unCutFields[rowNum].length; colNum++) {
            if (unCutFields[rowNum][colNum].equals(FieldState.UNHIT_SHIP)) {
                result = false;
                break;
            }
        }
        return result;
    }

    private FieldState[][] cutVertically(FieldState[][] unCutFields) {
        // TODO Auto-generated method stub
        return null;
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
}
