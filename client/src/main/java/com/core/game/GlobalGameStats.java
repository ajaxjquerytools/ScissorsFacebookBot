package com.core.game;

import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

/**
 * Created by VolodymyrD on 9/12/17.
 */
public class GlobalGameStats {

    private final static GlobalGameStats instance = new GlobalGameStats();

    public static GlobalGameStats getInstance() {
        return instance;
    }

    private Map<String, Multiset<GameTurn>> gameTurnStats = new HashMap<>();
    private Map<String, Multiset<GameResult>> gameResultStats = new HashMap<>();

    public void updateStats(String userId, GameTurn userTurn, GameResult gameResult) {

        gameTurnStats.putIfAbsent(userId, HashMultiset.create());
        gameResultStats.putIfAbsent(userId, HashMultiset.create());

        gameTurnStats.get(userId).add(userTurn);
        gameResultStats.get(userId).add(gameResult);
    }

    public String getGameTurnStats(String userid) {
        Multiset<GameTurn> gameTurns = gameTurnStats.get(userid);
        return String
                .format("- PAPER: %d, ROCK: %d, SCISSORS : %d\n", gameTurns.count(GameTurn.PAPER),
                        gameTurns.count(GameTurn.ROCK),
                        gameTurns.count(GameTurn.SCISSORS));
    }

    public String getGameResultStats(String userid) {
        Multiset<GameResult> gameResult = gameResultStats.get(userid);
        return String
                .format("- WON: %d, LOST: %d, DRAW: %d\n", gameResult.count(GameResult.WIN),
                        gameResult.count(GameResult.LOST),
                        gameResult.count(GameResult.DRAW));
    }
}
