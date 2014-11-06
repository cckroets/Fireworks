package com.ckroetsch.hanabi.game;

import com.ckroetsch.hanabi.model.Game;

/**
 * @author curtiskroetsch
 */
@Deprecated
public interface GameContext {

    Game get();

    int getId();

    String getName();
}
