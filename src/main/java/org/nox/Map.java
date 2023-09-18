package org.nox;

public class Map {
  private final int WIDTH = 10;
  private final int HEIGHT = 10;
  private final char[] head;
  private final char[][] cells;
  private final MapObject[][] objects;

  public Map() {
    head = new char[] {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'};
    cells = new char[WIDTH][HEIGHT];
    objects = new MapObject[WIDTH][HEIGHT];

    for (int y = 0; y < WIDTH; y++) {
      for (int x = 0; x < HEIGHT; x++) {
        cells[x][y] = ' ';
      }
    }
  }

  private void update() {

    clean();

    for (int y = 0; y < HEIGHT; y++) {
      for (int x = 0; x < WIDTH; x++) {
        MapObject object = objects[x][y];
        if (object != null) {
          Point position = object.getPosition();
          cells[position.x][position.y] = object.getAliveView();
        }
      }
    }
  }

  public void clean() {
    for (int y = 0; y < HEIGHT; y++) {
      for (int x = 0; x < WIDTH; x++) {

        cells[x][y] = ' ';
      }
    }
  }

  public void draw() {
    update();
    int coordX = 0;
    int coordY = 1;

    System.out.print(" ");

    for (int x = 0; x < WIDTH; x++) {
      System.out.print(" " + head[coordX]);
      coordX++;
    }
    System.out.println();
    for (int y = 0; y < HEIGHT; y++) {
      System.out.print(coordY);
      coordY++;

      for (int x = 0; x < WIDTH; x++) {
        if (!(x == 0 && coordY == 11)) {
          System.out.print(" ");
        }
        System.out.print(cells[x][y]);
      }

      System.out.println("|");
    }
  }

  public void addObject(MapObject object) {
    Point position = object.getPosition();
    if (isValidCoord(position)) {

      objects[position.x][position.y] = object;
    }
  }

  public boolean isValidCoord(Point point) {
    return point.x >= 0 && point.x < WIDTH && point.y >= 0 && point.y < HEIGHT;
  }

  public boolean isCollide(Point position) {
    return objects[position.x][position.y] != null;
  }
}
