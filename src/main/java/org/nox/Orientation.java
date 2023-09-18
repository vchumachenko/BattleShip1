package org.nox;

public enum Orientation {
    NONE(-1),
    HORIZONTAL(0),
    VERTICAL(1);

    private final int value;

    Orientation( int value ) {
        this.value = value;
    }

    public Point get_direction() {

        return value > 0 ? new Point(0, 1) : new Point(1, 0);
    }

    public static Orientation reverse(Orientation orient) {
        if(orient == Orientation.VERTICAL) {
            return Orientation.HORIZONTAL;
        }
        else if(orient == Orientation.HORIZONTAL) {
            return Orientation.VERTICAL;
        }
        return Orientation.NONE;
    }
}
