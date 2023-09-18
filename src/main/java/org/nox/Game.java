package org.nox;

public class Game {
    private GameState state;
    private final BasePlayer[] players;
    private int currentIndex;
    private boolean isGameOver;


    private void nextStep(){
        ++currentIndex;

        if (currentIndex >= players.length){
            currentIndex = 0;
        }
    }
    public Game() {
        state = GameState.FILL;
        players = new BasePlayer[2];
        players[0] = new Player(this);
        players[1] = new BasePlayer(this);
        currentIndex = 0;
        isGameOver = false;
    }
    public GameState getState() {
        return state;
    }


    public void process(){
        if( state != GameState.BATTLE &&
                players[0].isMapFilled() &&
                players[1].isMapFilled() )
        {
            state = GameState.BATTLE;
        }

        players[currentIndex].process();

        nextStep();
    }

    public void setGameOver() {
        isGameOver = true;
    }

    public boolean isGameOver() {
        return isGameOver;
    }
}
