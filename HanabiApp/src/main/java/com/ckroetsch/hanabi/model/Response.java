package com.ckroetsch.hanabi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author curtiskroetsch
 */
public final class Response {

    @JsonProperty("success")
    boolean mSuccess;

    @JsonProperty("game")
    Game mGame;

    private Response() {

    }

    public boolean isSuccess() {
        return mSuccess;
    }

    public Game getGame() {
        return mGame;
    }
}
