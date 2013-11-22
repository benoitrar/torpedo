package com.epam.livingpope.torpedo.shapes;

public class ShipShape {

    public final Field[][] fields;

    public ShipShape(Field[][] fields) {
        this.fields = fields;
    }
    
    public ShipShape(boolean[][] values) {
        validateValues(values);
        fields = createFieldsWithValidValues(values);
    }

    private Field[][] createFieldsWithValidValues(boolean[][] values) {
        Field fields[][] = new Field[values[0].length][values.length];
        for(int i=0;i<values[0].length;i++) {
            for(int j=0;j<values.length;j++) {
                fields[i][j] = createInitialFieldFromValue(values, i, j);
            }
        }
        return fields;
    }

    private Field createInitialFieldFromValue(boolean[][] values, int i, int j) {
        Field field;
        if(values[i][j]) {
            field = new Field(FieldState.UNHIT_SHIP);
        } else {
            field = new Field(FieldState.EMPTY);
        }
        return field;
    }

    private void validateValues(boolean[][] fields) {
        if(fields.length == 0 || fields[0].length == 0) {
            throw new IllegalArgumentException("Zero length field are prohibited");
        }
    }
}
