package com.ckroetsch.hanabi.network.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author curtiskroetsch
 */
public class SendMessageRequest {

    @JsonProperty("gameId")
    int mGameId;

    @JsonProperty("name")
    String mName;

    @JsonProperty("message")
    String mMessage;

    public SendMessageRequest(String name, int gameId, String message) {
        this.mName = name;
        this.mGameId = gameId;
        this.mMessage = message;
    }
}
