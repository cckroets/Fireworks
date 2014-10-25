package com.ckroetsch.hanabi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author curtiskroetsch
 */
public final class GameResponse {

    Boolean name;
    Game game;
    Integer cardIndex;

    private GameResponse() {

    }

    public boolean getName() {
        return name;
    }

    public Game getGame() {
        return game;
    }

    public int getCardIndex() {
        return cardIndex;
    }
}
