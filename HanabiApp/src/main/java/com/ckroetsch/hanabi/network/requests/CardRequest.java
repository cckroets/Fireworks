package com.ckroetsch.hanabi.network.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author curtiskroetsch
 */
public class CardRequest {

    @JsonProperty("gameId")
    int mGameId;

    @JsonProperty("name")
    String mName;

    @JsonProperty("cardIndex")
    int mCardIndex;

    public CardRequest(String name, int gameId, int cardIndex) {
        mGameId = gameId;
        mName = name;
        mCardIndex = cardIndex;
    }
}
