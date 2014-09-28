package com.ckroetsch.hanabi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author curtiskroetsch
 */
public final class GameId {

    @JsonProperty("id")
    int mId;

    private GameId() {

    }

    public int getId() {
        return mId;
    }
}
