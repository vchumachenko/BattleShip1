package org.nox;

public interface InputListener {
    boolean addNewShip(DeckCount decks, Orientation orient, Point start_coord );

    void attack( int x, int y );

    void quitGame();

    void autoFill();
}
