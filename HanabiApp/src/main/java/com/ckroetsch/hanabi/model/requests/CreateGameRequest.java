package com.ckroetsch.hanabi.model.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author curtiskroetsch
 */
public final class CreateGameRequest {

    @JsonProperty("name")
    String mName;

    @JsonProperty("rainbow")
    boolean mIsRainbow;

    public CreateGameRequest(String name, boolean rainbow) {
        mName = name;
        mIsRainbow = rainbow;
    }

    public String geName() {
        return mName;
    }

    public boolean isRainbow() {
        return mIsRainbow;
    }
}
