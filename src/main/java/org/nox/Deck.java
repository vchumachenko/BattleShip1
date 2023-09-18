package org.nox;

public class Deck implements MapObject {
  private final Point position;
  private final char deadView;
  private final char aliveView;
  private final boolean isAlive;

  public Deck(Point position, char deadView, char aliveView) {
    this.position = position;
    this.deadView = deadView;
    this.aliveView = aliveView;
    isAlive = true;
  }

  public boolean isAlive() {
    return isAlive;
  }

  @Override
  public Point getPosition() {
    return position;
  }

  @Override
  public char getAliveView() {
    return isAlive ? aliveView : deadView;
  }
}
