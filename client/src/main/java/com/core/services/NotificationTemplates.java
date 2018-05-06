package com.core.services;

import com.core.game.GameResult;
import com.core.game.GameTurn;

/**
 * Created by VolodymyrDvornyk on 10/4/2017.
 */
public interface NotificationTemplates<T> {

    T getRules();

    T getGameResult(String userId, GameTurn userTurn, GameTurn botTurn, GameResult userResult);
}
