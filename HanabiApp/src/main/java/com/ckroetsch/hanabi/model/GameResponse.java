package com.ckroetsch.hanabi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author curtiskroetsch
 */
public final class GameResponse {

    String name;
    Game game;
    Integer cardIndex;

    private GameResponse() {

    }

    public String getName() {
        return name;
    }

    public Game getGame() {
        return game;
    }

    public int getCardIndex() {
        return cardIndex;
    }
}
