package org.nox;

public enum DeckCount {

    INVALID(-1),
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4);
    private final int value;

    private static DeckCount [] map;
    static {
        map = new DeckCount[5];

        int counter = 0;

        for (DeckCount d : DeckCount.values()) {
            map[ counter++ ] = d;
        }
    }

    private DeckCount( int value ) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static DeckCount valueOf( int i ) {

        if( i >= 0 && i < 5 ){

            return map[i];
        }

        return INVALID;
    }
}
