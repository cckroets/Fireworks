package com.ckroetsch.hanabi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * @author curtiskroetsch
 */
public final class Game {

    Integer id;
    Integer numCardsRemaining;
    Boolean isRainbow;
    Integer numHints;
    Integer numLives;
    List<Player> players;
    List<Spectator> spectators;
    List<Card> played;
    List<Card> discarded;
    String currentPlayer;
    Boolean hasEnded;
    Boolean hasStarted;

    private Game() {
    }

    public int getId() {
        return id;
    }

    public int getCardsRemaining() {
        return numCardsRemaining;
    }

    public int getNumPlayers() {
        return players.size();
    }

    public boolean isRainbow() {
        return isRainbow;
    }

    public int getHints() {
        return numHints;
    }

    public int getLives() {
        return numLives;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public List<Spectator> getSpectators() {
        return spectators;
    }

    public List<Card> getPlayed() {
        return played;
    }

    public List<Card> getDiscarded() {
        return discarded;
    }

    public String getCurrentPlayerName() {
        return currentPlayer;
    }

            public boolean hasEnded() {
        return hasEnded;
    }

    public boolean hasStarted() {
        return hasStarted;
    }
}
