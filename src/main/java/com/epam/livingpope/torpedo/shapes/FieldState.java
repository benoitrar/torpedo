package com.epam.livingpope.torpedo.shapes;

public enum FieldState {

    UNHIT_EMPTY, HIT_EMPTY,
    UNHIT_SHIP, HIT_SHIP,
    UNSPECIFIED;
    
    public FieldState getStateOnHit() {
        FieldState stateOnHit;
        switch (this) {
        case UNSPECIFIED:
            throw new UnsupportedOperationException(
                    "Cannot hit unspecified field.");
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
}
