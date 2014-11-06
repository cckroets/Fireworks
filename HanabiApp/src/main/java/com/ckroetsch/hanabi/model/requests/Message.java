package com.ckroetsch.hanabi.model.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author curtiskroetsch
 */
public final class Message {

    @JsonProperty("name")
    String mName;

    @JsonProperty("message")
    String mMessage;

    public Message(String name, String message) {
        mName = name;
        mMessage = message;
    }

    private Message() {

    }

    public String getName() {
        return mName;
    }

    public String getMessage() {
        return mMessage;
    }
}
