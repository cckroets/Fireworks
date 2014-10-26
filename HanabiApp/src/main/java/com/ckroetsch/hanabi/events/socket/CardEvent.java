package com.ckroetsch.hanabi.events.socket;

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
