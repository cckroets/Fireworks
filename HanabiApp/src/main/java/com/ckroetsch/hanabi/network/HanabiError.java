package com.ckroetsch.hanabi.network;

/**
 * @author curtiskroetsch
 */
public class HanabiError {

    String event;
    String reason;

    private HanabiError() {

    }

    public String getEvent() {
        return event;
    }

    public String getReason() {
        return reason;
    }

}
