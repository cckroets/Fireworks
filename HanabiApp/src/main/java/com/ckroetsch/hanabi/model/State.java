package com.ckroetsch.hanabi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author curtiskroetsch
 */
public final class State {

    @JsonProperty("white")
    int mWhite;

    @JsonProperty("yellow")
    int mYellow;

    @JsonProperty("green")
    int mGreen;

    @JsonProperty("blue")
    int mBlue;

    @JsonProperty("red")
    int mRed;

    @JsonProperty("rainbow")
    int mRainbow;

    private State() {

    }

    public int getWhite() {
        return mWhite;
    }

    public int getBlue() {
        return mBlue;
    }

    public int getYellow() {
        return mYellow;
    }
    public int getGreen() {
        return mGreen;
    }
    public int getRed() {
        return mRed;
    }

    public int getRainbow() {
        return mRainbow;
    }

}
