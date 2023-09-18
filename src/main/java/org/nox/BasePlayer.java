package org.nox;

import java.util.List;
import java.util.Map;

public class BasePlayer {
  protected Game game;
  protected PlayerMap playerMap;
  protected Map<DeckCount,Integer> shipCounter;
  protected List<Ship> ships;
  protected int maxShips;
  protected int aliveShips;
  protected ShipFiller filler;

  public BasePlayer(Game game) {
    this.game = game;
    playerMap = new PlayerMap();

    init();
  }

  public final void init() {

    maxShips = 0;
    aliveShips = 0;
    filler = new ShipFiller(this);

    // Инициализация счетчика кораблей по их количеству палуб
    shipCounter = new java.util.HashMap<DeckCount, Integer>();
    for(int i = 1; i < DeckCount.values().length; ++i) {

      int ship_count = 5 - i;

      shipCounter.put( DeckCount.valueOf( i ), ship_count );

      // _max_ships = _max_ships + ship_count;
      maxShips += ship_count;
    }

    // Создание списка кораблей, если _max_ships больше нуля
    if( maxShips > 0 ) {
      ships = new java.util.ArrayList<Ship>();
    }
  }

  // Метод для получения количества свободных мест для кораблей определенного типа (по количеству палуб)
  public int get_free_places(DeckCount dc) {

    return shipCounter.get( dc );
  }

  // Метод для проверки, возможно ли разместить корабль в заданной ориентации, с заданным количеством палуб и начальными координатами
  public boolean is_posible_place(Orientation orient, DeckCount dc, Point start_coord) {

    Point step = orient.get_direction();

    boolean is_posible_place = true;

    Point position = new Point( start_coord.x, start_coord.y );

    for( int i = 0; i < dc.getValue(); ++i ) {

      // Проверяем, что координаты на карте допустимы, нет столкновений и нет соседних кораблей
      is_posible_place = playerMap.isValidCoord( position ) &&
              !playerMap.isCollide( position ) &&
              !playerMap.hasNeighbours( position );

      if( !is_posible_place ) {
        break;
      }

      position.x = position.x + step.x;
      position.y = position.y + step.y;
    }

    return is_posible_place;
  }

  // Метод для добавления корабля на карту игрока
  public boolean addShip( Orientation orient, DeckCount dc, Point start_coord ) {

    int free_places = get_free_places( dc );

    if( free_places > 0 )
    {
      Point[] coords = Ship.get_coords_for_ship(playerMap, dc, orient, start_coord);

      if( coords == null ) {

        return false;
      }

      Ship ship = new Ship( playerMap, dc, orient, coords );

      ships.add( ship );

      // Уменьшаем количество свободных мест для кораблей данного типа и увеличиваем количество живых кораблей
      free_places -= 1;
      shipCounter.replace( dc, free_places );
      ++aliveShips;

      return true;
    }

    return false;
  }

  // Метод для обработки игровой логики игрока
  public void process() {

    playerMap.draw(); // Отрисовываем карту игрока
  }

  // Метод для проверки, заполнена ли карта игрока всеми кораблями
  public boolean isMapFilled() {

    return maxShips == ships.size();
  }

  // Метод для получения количества живых кораблей
  public int get_alive_ships() {

    return aliveShips;
  }

  // Метод для получения карты игрока
  public PlayerMap get_map() {

    return playerMap;
  }
}
