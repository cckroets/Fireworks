package com.ckroetsch.hanabi.events.socket.play;

import com.ckroetsch.hanabi.model.Card;
import com.ckroetsch.hanabi.model.Game;

/**
 * @author curtiskroetsch
 */
public class CardEvent {
    public String name;
    public Game game;
    public Card card;
}
