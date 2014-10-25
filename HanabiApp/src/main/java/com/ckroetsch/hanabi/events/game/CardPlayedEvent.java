package com.ckroetsch.hanabi.events.game;

import com.ckroetsch.hanabi.model.Card;

/**
 * @author curtiskroetsch
 */
public class CardPlayedEvent implements GameCardPlayedEvent {

    boolean mSuccess;
    String mName;
    Card mCard;

    public CardPlayedEvent(String name, Card card, boolean success) {
        mSuccess = success;
        mCard = card;
        mName = name;
    }

    public boolean wasSuccessful() {
        return mSuccess;
    }

    public String getName() {
        return mName;
    }

    public Card getCard() {
        return mCard;
    }
}
