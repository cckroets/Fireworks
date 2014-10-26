package com.ckroetsch.hanabi.events.socket;

import com.ckroetsch.hanabi.model.Game;
import com.ckroetsch.hanabi.model.GameResponse;

/**
 * @author curtiskroetsch
 */
public class CreateGameEvent {
    public String name;
    public Game game;
}
