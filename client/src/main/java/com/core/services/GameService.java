package com.core.services;

import java.util.List;

import com.core.game.*;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Iterables;
import com.restfb.types.webhook.messaging.MessagingItem;
import com.core.services.facebook.FacebookNotificationService;

/**
 * Created by VolodymyrD on 9/12/17.
 */
public class GameService {

    private final static Logger logger = LoggerFactory.getLogger(GameService.class);

    private GlobalGameStats globalGameStats = GlobalGameStats.getInstance();

    private GameBot gameBot = GameBot.getInstance();


    private NotificationService<MessagingItem> notificationService = FacebookNotificationService.getInstance();

    private final static GameService instance = new GameService();

    public static GameService getInstance() {
        return instance;
    }

    public void playFullGame(List<MessagingItem> messagingItems) {

        MessagingItem lastMessage = Iterables.getLast(messagingItems, null);
        if (lastMessage == null) {
            return;
        }

        Pair<String, GameTurn> gameData = notificationService.getDestinationIdAndTurn(lastMessage);

        if (gameData == null) {
            //just ignore in case its empty
            logger.error("GameData is empty; Just skipp processing");
            return;
        }

        String userId = gameData.getKey();
        GameTurn userTurn = gameData.getRight();

        if (userTurn == null) {
            logger.info("UserId={}; Message don't contains user turn; Notifing gameRules", userId);
            notificationService.notifyAboutRules(userId);
            return;
        }

        try {
            GameResult gameResult = playWithBot(userId, userTurn);
            logger.info("GameResult for userId={} is {}", userId, gameResult.name());

        } catch (Exception ex) {
            logger.error("can't process game turn; logging");
            notificationService.notifyAboutRules(userId);
        }
    }

    /**
     * Use these method to process
     *
     * @param userId
     * @param userTurn
     * @return
     */
    public GameResult playWithBot(String userId, GameTurn userTurn) {

        GameTurn botTurn = gameBot.getNextBotTurn();
        GameResult gameResult = GameRule.getWinner(userTurn, botTurn);

        globalGameStats.updateStats(userId, userTurn, gameResult);
        notificationService.notifyGameResult(userId, userTurn, botTurn, gameResult);

        return gameResult;

    }

}
