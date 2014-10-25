package com.ckroetsch.hanabi.events;

import com.ckroetsch.hanabi.model.Message;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author curtiskroetsch
 */
public class MessageEvent {

    @JsonProperty("send_message")
    Message mMessage;

    public MessageEvent(Message message) {
        mMessage = message;
    }

    public Message getMessage() {
        return mMessage;
    }
}
