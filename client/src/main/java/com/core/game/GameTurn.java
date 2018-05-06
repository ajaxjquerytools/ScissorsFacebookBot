package com.core.game;

import com.restfb.types.webhook.messaging.MessageItem;

/**
 * Created by VolodymyrD on 9/12/17.
 */

public enum GameTurn {
    ROCK,
    PAPER,
    SCISSORS;

    /**
     * Method parcing turn from message we receive from user
     *
     * @param text
     * @return
     * @throws IllegalArgumentException
     */
    public static GameTurn parse(MessageItem messageItem) throws IllegalArgumentException {

        if (messageItem == null || messageItem.getText() == null) {
            return null;
        }

        try {
            return valueOf(messageItem.getText().toUpperCase().trim());
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }
}

