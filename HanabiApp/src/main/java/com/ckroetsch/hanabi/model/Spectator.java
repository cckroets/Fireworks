package com.ckroetsch.hanabi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author curtiskroetsch
 */
public final class Spectator {

    @JsonProperty("name")
    String mName;

    private Spectator() {

    }

    public String getName() {
        return mName;
    }
}
