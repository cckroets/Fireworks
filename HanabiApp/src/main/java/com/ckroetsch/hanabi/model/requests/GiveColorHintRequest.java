package com.ckroetsch.hanabi.model.requests;

import com.ckroetsch.hanabi.model.Suit;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author curtiskroetsch
 */
public class GiveColorHintRequest {

    @JsonProperty("name")
    String mName;

    @JsonProperty("toName")
    String mToName;

    @JsonProperty("color")
    Suit mSuit;

    public GiveColorHintRequest(String from, String to, Suit suit) {
        mName = from;
        mToName = to;
        mSuit = suit;
    }

    public String getName() {
        return mName;
    }

    public String getToName() {
        return mToName;
    }

    public Suit getColor() {
        return mSuit;
    }
}
