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

    @JsonProperty("numPlayers")
    int mNumPlayers;

    @JsonProperty("isRainbow")
    boolean mIsRainbow;

    @JsonProperty("numHints")
    int mHints;

    @JsonProperty("numLives")
    int mLives;

    @JsonProperty("players")
    List<Player> mPlayers;

    @JsonProperty("played")
    List<Card> mState;

    @JsonProperty("discarded")
    List<Card> mDiscarded;

    @JsonProperty("currentPlayer")
    String mCurrentPlayer;

    @JsonProperty("hasEnded")
    boolean mHasEnded;

    @JsonProperty("hasStarted")
    boolean mHasStarted;

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

    public void setCardsRemaining(int n) {
        mCardsRemaining = n;
    }

    public int getNumPlayers() {
        return mNumPlayers;
    }

    public void setNumPlayers(int numPlayers) {
        mNumPlayers = numPlayers;
    }

    public boolean isRainbow() {
        return mIsRainbow;
    }

    public void setRainbow(boolean value) {
        mIsRainbow = value;
    }

    public int getHints() {
        return mHints;
    }

    public void setHints(int hints) {
        mHints = hints;
    }

    public int getLives() {
        return mLives;
    }

    public void setLives(int i) {
        mLives = i;
    }

    public List<Player> getPlayers() {
        return mPlayers;
    }

    public void setHasStarted(boolean mHasStarted) {
        this.mHasStarted = mHasStarted;
    }

    public void setHasEnded(boolean mHasEnded) {
        this.mHasEnded = mHasEnded;
    }

    public void setCurrentPlayer(String mCurrentPlayer) {
        this.mCurrentPlayer = mCurrentPlayer;
    }

    public void setDiscarded(List<Card> mDiscarded) {
        this.mDiscarded = mDiscarded;
    }

    public void setState(List<Card> mState) {
        this.mState = mState;
    }

    public void setPlayers(List<Player> players) {
        mPlayers = players;

    }

    public List<Card> getState() {
        return mState;
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
