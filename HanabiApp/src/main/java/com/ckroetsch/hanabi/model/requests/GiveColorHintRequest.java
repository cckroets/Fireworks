package com.ckroetsch.hanabi.model.requests;

import com.ckroetsch.hanabi.model.Suit;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author curtiskroetsch
 */
public class GiveColorHintRequest {

    static final String COLOUR = "colour";

    @JsonProperty("gameId")
    int mGameId;

    @JsonProperty("name")
    String mHinter;

    @JsonProperty("toName")
    String mHintee;

    @JsonProperty("hintType")
    String mHintType;

    @JsonProperty("hint")
    String mHint;

    public GiveColorHintRequest(String name, int gameId, String hintee, Suit hint) {
        mGameId = gameId;
        mHinter = name;
        mHintee = hintee;
        mHintType = COLOUR;
        mHint = hint.name();
    }


}
