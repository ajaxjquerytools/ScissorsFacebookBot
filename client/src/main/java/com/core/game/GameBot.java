package com.core.game;

/**
 * Created by VolodymyrD on 9/12/17.
 */
public class GameBot {

    public static final GameBot instance = new GameBot();

    public static GameBot getInstance() {
        return instance;
    }

    private GlobalGameStats stats = GlobalGameStats.getInstance();


    public GameTurn getNextBotTurn() {
        //TODO: fix to correctly choose bot turn
        return GameTurn.PAPER;
    }

}
