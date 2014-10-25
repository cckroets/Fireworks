package com.ckroetsch.hanabi.events.game;

import com.ckroetsch.hanabi.model.Card;

/**
 * @author curtiskroetsch
 */
public class CardDiscardedEvent implements GameCardPlayedEvent {

    String mName;
    Card mCard;

    public CardDiscardedEvent(String name, Card card) {
        mName = name;
        mCard = card;
    }

    public Card getCard() {
        return mCard;
    }

    public String getName() {
        return mName;
    }
}
