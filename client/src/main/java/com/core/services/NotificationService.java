package com.core.services;

import org.apache.commons.lang3.tuple.Pair;

import com.core.game.GameResult;
import com.core.game.GameTurn;

/**
 * Created by VolodymyrDvornyk on 10/3/2017.
 */
public interface NotificationService<T> {

    Pair<String, GameTurn> getDestinationIdAndTurn(T msg) throws IllegalArgumentException;

    void notifyAboutRules(String destination);

    void notifyGameResult(String destination, GameTurn userTurn, GameTurn botTurn, GameResult gameResult);


}
