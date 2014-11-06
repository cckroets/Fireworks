package com.ckroetsch.hanabi.events.socket.play;

import com.ckroetsch.hanabi.events.socket.GameChangeEvent;
import com.ckroetsch.hanabi.model.Card;
import com.ckroetsch.hanabi.model.Game;

/**
 * @author curtiskroetsch
 */
public class CardEvent extends GameChangeEvent {
    public Card card;
}
