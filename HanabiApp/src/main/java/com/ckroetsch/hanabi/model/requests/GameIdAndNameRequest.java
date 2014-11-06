package com.ckroetsch.hanabi.model.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author curtiskroetsch
 */
public class GameIdAndNameRequest {

    @JsonProperty("gameId")
    int mGameId;

    @JsonProperty("name")
    String mName;

    public GameIdAndNameRequest(String name, int gameId) {
        this.mName = name;
        this.mGameId = gameId;
    }
}
