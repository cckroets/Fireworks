package com.ckroetsch.hanabi.model.requests;

import com.ckroetsch.hanabi.model.Card;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author curtiskroetsch
 */
public final class PlayCardRequest {

    @JsonProperty("name")
    String mName;

    @JsonProperty("card")
    Card mCard;

    public PlayCardRequest(String name, Card card) {
        mName = name;
        mCard = card;
    }

    private PlayCardRequest() {

    }

    public Card getCard() {
        return mCard;
    }

    public String getName() {
        return mName;
    }

}
