package com.epam.livingpope.torpedo.shapes;

public class ShipShape {

    public final FieldState[][] fields;

    public ShipShape(FieldState[][] fields) {
        this.fields = fields;
    }
    
    public ShipShape(boolean[][] values) {
        validateValues(values);
        fields = createFieldsWithValidValues(values);
    }

    private FieldState[][] createFieldsWithValidValues(boolean[][] values) {
        FieldState fields[][] = new FieldState[values[0].length][values.length];
        for(int i=0;i<values[0].length;i++) {
            for(int j=0;j<values.length;j++) {
                fields[i][j] = createInitialFieldFromValue(values, i, j);
            }
        }
        return fields;
    }

    private FieldState createInitialFieldFromValue(boolean[][] values, int i, int j) {
        FieldState field;
        if(values[i][j]) {
            field = FieldState.UNHIT_SHIP;
        } else {
            field = FieldState.UNHIT_EMPTY;
        }
        return field;
    }

    private void validateValues(boolean[][] fields) {
        if(fields.length == 0 || fields[0].length == 0) {
            throw new IllegalArgumentException("Zero length field are prohibited");
        }
    }
}
