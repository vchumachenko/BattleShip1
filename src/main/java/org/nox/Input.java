package org.nox;

import java.util.Scanner;

public class Input {
  private Scanner scanner = new Scanner(System.in);
  private InputListener listener;
  private final String baseFillMessage = "input %d-deck ship in format %s:";
  private String currentMessage;
  private int currentShipDeck;

  public Input(InputListener listener) {


    this.listener = listener;
    currentShipDeck = 1;

    update_message();
  }

  void update_message() {

    String format = currentShipDeck > 1 ? "(x,y;orientation)" : "(x,y)";

    currentMessage = String.format(baseFillMessage, currentShipDeck, format );
  }

  public void process(GameState state) {

    System.out.println(currentMessage);

    String in = scanner.nextLine();

    System.out.print( in );

    boolean quit_game = "q".equals(in) || "quit".equals(in);

    if( listener != null ) {

      if ( quit_game ) {

        listener.quitGame();

        return;
      }

      if( state == GameState.FILL ) {

        fill_process( in );
      }
      else if( state == GameState.BATTLE ) {

        do_process(in);
      }
    }
  }

  private Point parse_coords(String in) {

    String [] coords = in.split(",");
    int x = -1;
    int y = -1;

    if( coords.length >= 2 ) {

      char symbol = Character.toLowerCase( coords[0].charAt(0) );
      x = symbol - 'a'; // a - 55 --> 0 - 9
      y = Integer.parseInt( coords[1] ) - 1;
    }

    return new Point(x, y);
  }

  private void fill_process(String in) {

    // A-J, 1-10; h/v; 1-4
    // B, 4; H; 3
    String [] chunks = in.split(";");

    if( chunks.length == 1 && "auto".equals(in) )
    {
      listener.autoFill();
    }

    DeckCount dc = DeckCount.valueOf(currentShipDeck);

    if( chunks.length >= 1 ) {

      Point c = parse_coords(chunks[0]);
      Orientation orient = Orientation.NONE;
      if( currentShipDeck == 1 )
      {
        orient = Orientation.HORIZONTAL;
      }
      else if( "H".equals( chunks[1] ) || "h".equals( chunks[1] ) ) {

        orient = Orientation.HORIZONTAL;
      }
      else if( "V".equals( chunks[1] ) || "v".equals( chunks[1] ) ) {

        orient = Orientation.VERTICAL;
      }

      if( orient != Orientation.NONE && c.x >= 0 && c.y >= 0 ) {

        if( !listener.addNewShip( dc, orient, new Point(c.x, c.y) ) ) {

          ++currentShipDeck;
          update_message();
        }
      }
    }
  }

  private void do_process( String in ) {

    Point c = parse_coords(in);

    if( c.x >= 0 && c.y >= 0 ) {

      listener.attack(c.x, c.y);
    }
  }
}
