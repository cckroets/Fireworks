package com.ckroetsch.hanabi.model.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author curtiskroetsch
 */
public class CreateGameRequest {

    @JsonProperty("isRainbow")
    boolean mIsRainbow;

    @JsonProperty("name")
    String mName;

    public CreateGameRequest(String name, boolean isRainbow) {
        this.mName = name;
        this.mIsRainbow = isRainbow;
    }

}
