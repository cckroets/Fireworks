package com.ckroetsch.hanabi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * @author curtiskroetsch
 */
public final class Player {

    @JsonProperty("name")
    String mName;

    @JsonProperty("hand")
    List<Card> mHand;

    private Player() {

    }

    public String getName() {
        return mName;
    }

    public List<Card> getHand() {
        return mHand;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public void setHand(List<Card> mHand) {
        this.mHand = mHand;
    }
}
