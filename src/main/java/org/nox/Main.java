package org.nox;

import java.io.IOException;


public class Main {
  public static void main(String[] args) throws IOException {
    Map payerMap = new Map();
    Map enemyMap = new Map();

    Input in = new Input(null);
    GameState gs = GameState.FILL;

    Ship shipD3 = new Ship(payerMap,DeckCount.THREE,Orientation.VERTICAL, new Point(2, 4));

    while (!in.isDone()) {

      System.out.println("------- My map -------");
      payerMap.draw();

      System.out.println("------ Enemy map -----");
      enemyMap.draw();
      in.process(gs);
    }
  }
}
