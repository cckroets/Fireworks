package com.ckroetsch.hanabi.network;

import com.ckroetsch.hanabi.model.Suit;

/**
 * @author curtiskroetsch
 */
public interface HanabiFrontEndAPI {

    void createGame(String name, boolean isRainbow);

    void enterGame(String name, int gameId);

    void resumeGame(String name, int gameId);

    void joinGame(String name, int gameId);

    void startGame(int gameId);

    void sendMessage(String name, int gameId, String message);

    void giveColorHint(String hinter, int gameId, Suit suit, String hintee);

    void giveNumberHint(String hinter, int gameId, int num, String hintee);

    void discardCard(String name, int gameId, int cardIndex);

    void playCard(String name, int gameId, int cardIndex);

    void endGame(String name, int gameId);
}
