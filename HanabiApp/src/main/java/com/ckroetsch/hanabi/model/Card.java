package com.ckroetsch.hanabi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author curtiskroetsch
 */
public final class Card {

    @JsonProperty("suit")
    String mSuit;

    @JsonProperty("number")
    int mValue;

    private Card() {

    }

    public String getSuit() {
        return mSuit;
    }

    public int getValue() {
        return mValue;
    }
}
