package com.ckroetsch.hanabi.model.requests;

import com.ckroetsch.hanabi.model.Suit;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author curtiskroetsch
 */
public class GiveNumberHintRequest {

    @JsonProperty("name")
    String mName;

    @JsonProperty("toName")
    String mToName;

    @JsonProperty("number")
    int mVaule;

    public GiveNumberHintRequest(String from, String to, int value) {
        mName = from;
        mToName = to;
        mVaule = value;
    }

    public String getName() {
        return mName;
    }

    public String getToName() {
        return mToName;
    }

    public int getValue() {
        return mVaule;
    }
}
