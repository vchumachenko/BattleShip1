package org.nox;



public class Ship {
  private final Deck[] decks;
  private final DeckCount deckCount;
  private final Orientation orientation;
  private final Point[] coords;

  public Ship() {

    decks = null;
    deckCount = DeckCount.INVALID;
    orientation = Orientation.NONE;
    coords = null;
  }

  public Ship(PlayerMap map, DeckCount deck_count, Orientation orientation, Point[] coords) {

    deckCount = deck_count;
    this.orientation = orientation;
    this.coords = coords;

    decks = new Deck[deck_count.getValue()];

    for (int i = 0; i < decks.length; ++i) {

      Deck deck = new Deck(coords[i], 'O', 'X');

      decks[i] = deck;

      map.addObject(deck);
    }
  }

  public DeckCount getDeckCount() {

    return deckCount;
  }

  public boolean is_valid() {

    return deckCount != DeckCount.INVALID && orientation != Orientation.NONE && coords != null;
  }

  public boolean is_alive() {

    boolean is_alive = false;

    for (Deck deck : decks) {

      if (deck.isAlive()) {

        is_alive = true;
      }
    }

    return is_alive;
  }

  public static Point[] get_coords_for_ship(
      PlayerMap map, DeckCount deck_count, Orientation orientation, Point start_coord) {

    Point step = orientation.get_direction();

    boolean is_posible_place = true;

    Point[] coords = new Point[deck_count.getValue()];
    Point position = new Point(start_coord.x, start_coord.y);

    for (int i = 0; i < deck_count.getValue(); ++i) {

      is_posible_place =
          map.isValidCoord(position) && !map.isCollide(position) && !map.hasNeighbours(position);

      if (!is_posible_place) {
        break;
      }

      coords[i] = new Point(position.x, position.y);

      position.x = position.x + step.x;
      position.y = position.y + step.y;
    }

    if (is_posible_place) {

      return coords;
    }

    return null;
  }
}
