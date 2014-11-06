package com.ckroetsch.hanabi.model;

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
    Integer score;
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

    public int getNumCardsRemaining() {
        return numCardsRemaining;
    }

    public boolean getIsRainbow() {
        return isRainbow;
    }

    public int getNumHints() {
        return numHints;
    }

    public int getNumLives() {
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

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean getHasEnded() {
        return hasEnded;
    }

    public boolean getHasStarted() {
        return hasStarted;
    }

    public int getScore() {
        return score;
    }
}
