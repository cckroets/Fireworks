package com.ckroetsch.hanabi.model.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author curtiskroetsch
 */
public class GiveNumberHintRequest {

    static final String NUMBER = "number";

    @JsonProperty("gameId")
    int mGameId;

    @JsonProperty("name")
    String mHinter;

    @JsonProperty("toName")
    String mHintee;

    @JsonProperty("hintType")
    String mHintType;

    @JsonProperty("hint")
    int mHint;

    public GiveNumberHintRequest(String name, int gameId, String hintee, int hint) {
        mGameId = gameId;
        mHinter = name;
        mHintee = hintee;
        mHintType = NUMBER;
        mHint = hint;
    }
}
