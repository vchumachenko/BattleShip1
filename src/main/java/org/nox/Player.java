package org.nox;

public class Player extends BasePlayer implements InputListener {

  private Input input;

  public Player( Game game ) {

    super( game );

    input = new Input( this );
  }

  public boolean addNewShip(DeckCount decks, Orientation orient, Point startCoord ) {

    System.out.println( "make_ship x, y (" + startCoord.x + ", " + startCoord.y + ") " + decks );

    addShip( orient, decks, startCoord );

    return get_free_places(decks) > 0;
  }

  public void attack( int x, int y ) {

  }

  public void quitGame() {

    game.setGameOver();
  }

  @Override
  public void process() {

    super.process();

    input.process( game.getState() );
  }

  public void autoFill() {

    filler.fill();
  }
}
