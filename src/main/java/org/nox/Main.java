package org.nox;

import java.io.IOException;


public class Main {
  public static void main(String[] args) throws IOException {
    Game game = new Game();


    while (!game.isGameOver()) {

      game.process();
    }
  }
}
