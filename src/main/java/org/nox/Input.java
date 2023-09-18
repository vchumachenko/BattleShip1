package org.nox;

import java.util.Scanner;

public class Input {
  boolean done = false;
  Scanner scanner = new Scanner(System.in);
  InputListener inputListener;



  public Input(InputListener listener) {
    inputListener = listener;
  }

  public boolean isDone() {
    return done;
  }

  public void process(GameState state) {
    String in = scanner.nextLine();
    done = in.equals("q") || in.equals("quit");

    if (!done && inputListener != null) {
      if (state == GameState.FILL) {
        fillProcess(in);
      } else if (state == GameState.PROCESS) {
        doProcess(in);
      }
    }
  }

  private void doProcess(String in) {
    Point c = parseCoords(in);
    if (c.x >= 0 && c.y >= 0){
      inputListener.attack(c.x,c.y);
    }
  }

  private void fillProcess(String in) {
    String[] chunks = in.split(";");

    if (chunks.length >= 3) {
      Point c = parseCoords(chunks[0]);

      int palubs = Integer.parseInt(chunks[2]);
      Orientation orientation = Orientation.NONE;

      if (chunks[1].equals("H") || chunks[1].equals("h")) {
        orientation = Orientation.HORIZONTAL;

      } else if (chunks[1].equals("V") || chunks[1].equals("v")) {
        orientation = Orientation.VERTICAL;
      }
      if (orientation != Orientation.NONE && c.x >= 0 && c.y >= 0) {
        inputListener.makeShip(c.x, c.y, palubs, orientation);
      }
    }
  }

  private Point parseCoords(String in) {
    String[] coords = in.split(",");
    int x = -1;
    int y = -1;

    if (coords.length >= 2) {
      char symbol = Character.toLowerCase(coords[0].charAt(0));
      x = symbol - 'a';
      y = Integer.parseInt(coords[1]);
    }
    return new Point(x,y);
  }
}
