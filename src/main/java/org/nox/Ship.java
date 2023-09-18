package org.nox;

enum DeckCount {
  INVALID(-1),
  ONE(1),
  TWO(2),
  THREE(3),
  FOUR(4);
  private final int value;

  DeckCount(int value) {
    this.value = value;
  }

  public int getValue() {
    return value;
  }
}

public class Ship {
  private Deck[] decks;
  private final DeckCount deckCount;
  private final Orientation orientation;
  private final Point[] coords;

  public Ship() {
    decks = null;
    this.deckCount = DeckCount.INVALID;
    this.orientation = Orientation.NONE;
    this.coords = null;

  }

  public Ship(Map map,DeckCount deckCount, Orientation orientation, Point[] coords) {
    this.deckCount = deckCount;
    this.orientation = orientation;
    this.coords = coords;

    decks = new Deck[deckCount.getValue()];

    for (int i = 0; i < decks.length; i++) {

      Deck deck = new Deck(coords[i], '0', 'X');

      decks[i] = deck;

      map.addObject(deck);
    }
  }

  public boolean isValid() {
    return deckCount != DeckCount.INVALID &&
            orientation != Orientation.NONE &&
            coords != null;
  }

  public boolean isAlive() {
    boolean isAlive = false;

    for (Deck deck : decks) {
      if (deck.isAlive()) {
        isAlive = true;
      }
    }
    return isAlive;
  }

  public static Ship make(Map map, DeckCount deckCount, Orientation orientation, Point startCoord) {

    Point step = new Point(0,0);

    if (orientation == Orientation.HORIZONTAL) {

      step = new Point(1, 0);

    } else if (orientation == Orientation.VERTICAL) {

      step = new Point(0, 1);

    }else {
      return null;
    }

    boolean isPossiblePlace = true;

    Point[] coords = new Point[deckCount.getValue()];
    Point position = new Point(startCoord.x, startCoord.y);

    for (int i = 0; i < deckCount.getValue(); i++) {

      isPossiblePlace = map.isValidCoord(position) && !map.isCollide(position);

      if (!isPossiblePlace){
        break;
      }

      coords[i] = new Point(position.x, position.y);

      position.x += step.x;
      position.y += step.y;
    }

    if (isPossiblePlace) {
      return new Ship(map, deckCount, orientation, coords);
    }
    return null;
  }
}
