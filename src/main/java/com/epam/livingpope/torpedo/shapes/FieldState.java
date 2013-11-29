package com.epam.livingpope.torpedo.shapes;

public enum FieldState {

    UNHIT_EMPTY(" "), HIT_EMPTY("."), UNHIT_SHIP("X"), HIT_SHIP("#"), UNSPECIFIED("?");

    private String view;

    private FieldState(String view) {
        this.view = view;
    }

    public FieldState getStateOnHit() {
        FieldState stateOnHit;
        switch (this) {
        case UNSPECIFIED:
            throw new UnsupportedOperationException("Cannot hit unspecified field.");
        case UNHIT_EMPTY:
            stateOnHit = HIT_EMPTY;
            break;
        case UNHIT_SHIP:
            stateOnHit = HIT_SHIP;
            break;
        default:
            stateOnHit = this;
        }
        return stateOnHit;
    }

    public boolean isUnhitShip() {
        return this.equals(UNHIT_SHIP);
    }

    @Override
    public String toString() {
        return view;
    }
}
