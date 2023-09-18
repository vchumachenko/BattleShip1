package org.nox;

import java.util.Random;

public class ShipFiller {
    private final BasePlayer player;

    public ShipFiller(BasePlayer player) {

        this.player = player;
    }

    public void fill( ) {

        Random rand = new Random();
        java.util.List<Point> map_points = new java.util.ArrayList<Point>();

        for( int x = 0; x < PlayerMap.WIDTH; ++x ) {

            for( int y = 0; y < PlayerMap.HEIGHT; ++y ) {

                map_points.add( new Point( x, y ) );
            }
        }


        for(int i = 1; i < DeckCount.values().length; ++i) {

            DeckCount dc = DeckCount.valueOf(i);

            while( player.get_free_places(dc) > 0 ) {

                Orientation orient = rand.nextInt(2) > 0 ? Orientation.HORIZONTAL
                        : Orientation.VERTICAL;

                do
                {
                    // we get number from 0 to map_points.size()
                    int index = rand.nextInt( map_points.size() );

                    Point coord = map_points.get( index );

                    map_points.remove( index );

                    Orientation reverse_orient = Orientation.reverse( orient );

                    if( player.is_posible_place( reverse_orient, dc, coord ) )
                    {
                        player.addShip( reverse_orient, dc, coord );

                        break;
                    }
                    else if( player.is_posible_place( orient, dc, coord) ) {

                        player.addShip( orient, dc, coord );

                        break;
                    }
                }
                while( !map_points.isEmpty() );
            }
        }
    }}
