package com.ckroetsch.hanabi.events.game;

import com.ckroetsch.hanabi.model.Card;

/**
 * @author curtiskroetsch
 */
public interface GameCardPlayedEvent {
    String getName();
    Card getCard();
}
