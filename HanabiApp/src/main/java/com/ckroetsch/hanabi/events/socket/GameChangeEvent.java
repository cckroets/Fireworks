package com.ckroetsch.hanabi.events.socket;

import com.ckroetsch.hanabi.game.GameContext;
import com.ckroetsch.hanabi.model.Game;

/**
 * @author curtiskroetsch
 */
public class GameChangeEvent implements GameContext {
    public Game game;
    public String name;

    @Override
    public Game get() {
        return game;
    }

    @Override
    public int getId() {
        return game.getId();
    }

    @Override
    public String getName() {
        return name;
    }
}
