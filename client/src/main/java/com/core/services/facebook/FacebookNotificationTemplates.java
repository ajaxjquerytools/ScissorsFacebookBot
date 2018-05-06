package com.core.services.facebook;

import com.core.services.NotificationTemplates;
import com.restfb.types.send.Message;
import com.core.game.GameResult;
import com.core.game.GameTurn;
import com.core.game.GlobalGameStats;

/**
 * Created by VolodymyrDvornyk on 10/4/2017.
 */
public class FacebookNotificationTemplates implements NotificationTemplates<Message> {

    private final static FacebookNotificationTemplates instance = new FacebookNotificationTemplates();

    private GlobalGameStats globalGameStats = GlobalGameStats.getInstance();

    public static FacebookNotificationTemplates getInstance() {
        return instance;
    }

    public Message getRules() {
        final String rules = "Game Rock-paper-scissors | RULES:\n" +
                "- Rock wins against Scissors,\n" +
                "- Scissors wins against Paper\n" +
                "- Paper wins against Rock\n" +
                " ======================== \n" +
                "INPUT: ROCK | PAPER | SCISSORS";

        return new Message(rules);
    }

    public Message getGameResult(String userId, GameTurn userTurn, GameTurn botTurn, GameResult userResult) {
        String result = String
                .format("RESULT: %s, \n Your turn: %s, bot turn: %s \n", userResult.name(), userTurn.name(),
                        botTurn.name());
        String userStats = "Your statistics: \n";
        String resultStats = globalGameStats.getGameResultStats(userId);
        String turnStats = globalGameStats.getGameTurnStats(userId);
        String input = " ======================== \n" + "INPUT: ROCK | PAPER | SCISSORS";

        String gameResult = result + userStats + resultStats + turnStats + input;

        return new Message(gameResult);
    }

}
