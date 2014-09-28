package com.ckroetsch.hanabi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author curtiskroetsch
 */
public final class GameResponse {

    @JsonProperty("success")
    boolean mSuccess;

    @JsonProperty("game")
    Game mGame;

    private GameResponse() {

    }

    public boolean isSuccess() {
        return mSuccess;
    }

    public Game getGame() {
        return mGame;
    }
}
