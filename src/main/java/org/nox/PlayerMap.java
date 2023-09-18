package org.nox;

import java.util.Map;
import java.util.logging.Logger;

public class PlayerMap {
  public static Logger log = Logger.getLogger( Map.class.getName() );

  public static final int WIDTH = 10;
  public static final int HEIGHT = 10;

  private final char [] head;
  private final char [][] cells;
  private final char [][] map_view;

  private final MapObject[][] objects;

  Point[] neighbours;

  public PlayerMap() {

    head = new char[] { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J' };
    cells = new char[HEIGHT][WIDTH];
    map_view = new char[HEIGHT + 1] [(WIDTH + 1) * 2];

    objects = new MapObject[HEIGHT][WIDTH];

    for(  int y = 0; y < HEIGHT; ++y ) {

      for( int x = 0; x < WIDTH; ++x ) {

        cells[y][x] = ' ';
      }
    }

    neighbours = new Point[8];
    neighbours[0] = new Point( -1, -1 );
    neighbours[1] = new Point( -1, 0 );
    neighbours[2] = new Point( -1, 1 );
    neighbours[3] = new Point( 0, 1 );
    neighbours[4] = new Point( 1, 1 );
    neighbours[5] = new Point( 1, 0 );
    neighbours[6] = new Point( 1, -1 );
    neighbours[7] = new Point( 0, -1 );
  }

  public void addObject(MapObject object) {

    Point position = object.getPosition();

    if ( isValidCoord( position ) ) {

      objects[position.y][position.x] = object;
    }
    else {

      log.warning( "can't add map object" );
    }
  }

  public boolean isValidCoord( Point point ) {

    return point.x >= 0 && point.x < WIDTH && point.y >= 0 && point.y < HEIGHT;
  }

  public boolean isCollide( Point position ) {

    return objects[position.y][position.x] != null;
  }

  public boolean hasNeighbours( Point position ) {

    boolean result = false;

    for( Point p : neighbours) {

      Point neighbour = new Point( position.x + p.x, position.y + p.y );

      if( isValidCoord( neighbour ) && objects[neighbour.y][neighbour.x] != null ) {

        result = true;
        break;
      }
    }

    return result;
  }

  public void clean() {

    for(  int y = 0; y < HEIGHT; ++y ) {

      for( int x = 0; x < WIDTH; ++x ) {

        cells[y][x] = ' ';
      }
    }
  }

  private void update() {

    clean();

    for(  int y = 0; y < HEIGHT; ++y ) {

      for( int x = 0; x < WIDTH; ++x ) {

        MapObject object = objects[y][x];

        if( object != null ) {

          Point position = object.getPosition();

          cells[position.y][position.x] = object.getView();
        }
      }
    }
  }

  public void draw() {

    update();

    int coordX = 0;
    int coordY = 1;

    //System.out.print( ' ' );
    map_view[0][0] = ' ';
    map_view[0][1] = ' ';
    int width = (WIDTH + 1) * 2;
    for( int x = 2; x < width; x += 2 ) {

      map_view[0][x] = head[coordX];
      map_view[0][x+1] = ' ';

      coordX += 1;
    }

    for(  int y = 0; y < HEIGHT; ++y ) {

      String row_number = String.valueOf(coordY);
      map_view[coordY][0] = row_number.charAt(0);

      if( coordY > 9 ) {
        map_view[coordY][1] = row_number.charAt(1);
      }
      else {
        map_view[coordY][1] = ' ';
      }
      coordX = 2;
      for( int x = 0; x < WIDTH; ++x ) {

        map_view[coordY][coordX++] = cells[y][x];
        map_view[coordY][coordX++] = ' ';
      }
      ++coordY;

    }

    for(int y = 0; y < (HEIGHT+1); ++y){

      for(int x = 0; x < width; ++x){

        System.out.print(map_view[y][x]);
      }
      System.out.println();
    }
  }
}
