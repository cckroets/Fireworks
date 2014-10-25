package com.ckroetsch.hanabi.events.socket;

import com.ckroetsch.hanabi.network.HanabiError;

/**
 * @author curtiskroetsch
 */
public class HanabiErrorEvent {

    HanabiError error;

    public HanabiErrorEvent(HanabiError error) {
        this.error = error;
    }

    public HanabiError getError() {
        return error;
    }
}
