package com.ckroetsch.hanabi.events.socket;

import com.ckroetsch.hanabi.model.Game;

/**
 * @author curtiskroetsch
 */
public class PlayCardEvent {
    public String name;
    public Game game;
    public Integer cardIndex;
}
