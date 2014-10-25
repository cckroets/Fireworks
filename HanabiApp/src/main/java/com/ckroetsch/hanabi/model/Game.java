package com.ckroetsch.hanabi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * @author curtiskroetsch
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public final class Game {

    @JsonProperty("id")
    int mId;

    @JsonProperty("numCardsRemaining")
    int mCardsRemaining;

    @JsonProperty("isRainbow")
    boolean mIsRainbow;

    @JsonProperty("numHints")
    int mHints;

    @JsonProperty("numLives")
    int mLives;

    @JsonProperty("players")
    List<Player> mPlayers;

    @JsonProperty("spectators")
    List<Spectator> mSpectators;

    @JsonProperty("played")
    List<Card> mPlayed;

    @JsonProperty("discarded")
    List<Card> mDiscarded;

    @JsonProperty("currentPlayer")
    String mCurrentPlayer;

    @JsonProperty("hasEnded")
    boolean mHasEnded;

    @JsonProperty("hasStarted")
    boolean mHasStarted;

    @JsonProperty("state")
    Object object;

    private Game() {

    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public int getCardsRemaining() {
        return mCardsRemaining;
    }

    public int getNumPlayers() {
        return mPlayers.size();
    }

    public boolean isRainbow() {
        return mIsRainbow;
    }

    public int getHints() {
        return mHints;
    }

    public int getLives() {
        return mLives;
    }

    public List<Player> getPlayers() {
        return mPlayers;
    }

    public List<Spectator> getSpectators() {
        return mSpectators;
    }

    public List<Card> getPlayed() {
        return mPlayed;
    }

    public List<Card> getDiscarded() {
        return mDiscarded;
    }

    public String getCurrentPlayerName() {
        return mCurrentPlayer;
    }

    public boolean hasEnded() {
        return mHasEnded;
    }

    public boolean hasStarted() {
        return mHasStarted;
    }
}
