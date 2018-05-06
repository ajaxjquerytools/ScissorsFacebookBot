package com.core.services.facebook;

import com.core.services.NotificationService;
import com.core.services.NotificationTemplates;
import com.core.game.GameResult;
import com.core.game.GameTurn;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.types.send.*;
import com.restfb.types.webhook.messaging.MessageItem;
import com.restfb.types.webhook.messaging.MessagingItem;
import com.restfb.types.webhook.messaging.MessagingParticipant;

import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by VolodymyrD on 9/8/17.
 */
public class FacebookNotificationService implements NotificationService<MessagingItem> {

    private Logger logger = LoggerFactory.getLogger(FacebookNotificationService.class);


    private String PAGE_ACCESS_TOCKEN = "EAAbwqJZAfCccBAM1oqFZAZBDRfxkBnh9Ppuf5w1Mn6jzyNLAdQXVH8ErZBgZCiBUvHuMg4R6532ZB2wqdqBCQNpr2HfalmGHs623dzFvwujeEOXH2pwesM5Q52FoqZCtnvwBYtZBTw8kL9fJBa46CSy7hZC5mHvsht7rLXk205nYOSgZDZD";

    private FacebookClient pageClient;

    private final static FacebookNotificationService instance = new FacebookNotificationService();

    private NotificationTemplates<Message> notificationTemplates = FacebookNotificationTemplates.getInstance();

    public FacebookNotificationService() {
        pageClient = new DefaultFacebookClient(PAGE_ACCESS_TOCKEN, Version.VERSION_2_6);
    }

    public static FacebookNotificationService getInstance() {
        return instance;
    }

    public void sendMessage(String userId, Message message) {
        IdMessageRecipient recipient = new IdMessageRecipient(userId);

        SendResponse resp = pageClient.publish("me/messages", SendResponse.class,
                Parameter.with("recipient", recipient), // the id or phone recipient
                Parameter.with("message", message)); // one of the messages from above

        logger.info(resp.toString());

    }

    /**
     *  Processing only last recieved message
     */
    @Override
    public Pair<String, GameTurn> getDestinationIdAndTurn(final MessagingItem messagingItem)
            throws IllegalArgumentException {

        if (messagingItem == null || messagingItem.getSender() == null) {
            return null;
        }

        MessagingParticipant sender = messagingItem.getSender();
        MessageItem messageItem = messagingItem.getMessage();
        GameTurn gameTurn = GameTurn.parse(messageItem);
        return Pair.of(sender.getId(), gameTurn);

    }

    @Override
    public void notifyAboutRules(final String destination) {
        Message message = notificationTemplates.getRules();
        sendMessage(destination, message);
    }

    @Override
    public void notifyGameResult(final String destination, GameTurn userTurn, GameTurn botTurn, GameResult gameResult) {
        Message message = notificationTemplates.getGameResult(destination, userTurn, botTurn, gameResult);
        sendMessage(destination, message);
    }
}
