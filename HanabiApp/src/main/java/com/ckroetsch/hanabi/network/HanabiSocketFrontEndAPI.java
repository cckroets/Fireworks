package com.ckroetsch.hanabi.network;

import com.ckroetsch.hanabi.events.socket.SocketEvent;
import com.ckroetsch.hanabi.network.requests.CardRequest;
import com.ckroetsch.hanabi.network.requests.CreateGameRequest;
import com.ckroetsch.hanabi.model.Suit;
import com.ckroetsch.hanabi.network.requests.GameIdAndNameRequest;
import com.ckroetsch.hanabi.network.requests.GiveColorHintRequest;
import com.ckroetsch.hanabi.network.requests.GiveNumberHintRequest;
import com.ckroetsch.hanabi.network.requests.SendMessageRequest;
import com.google.inject.Inject;

/**
 * @author curtiskroetsch
 */
public class HanabiSocketFrontEndAPI implements HanabiFrontEndAPI {

    @Inject
    HanabiSocketIO mSocket;


    @Inject
    private HanabiSocketFrontEndAPI() {

    }

    @Override
    public void createGame(String name, boolean isRainbow) {
        mSocket.emit(SocketEvent.CREATE_GAME.getEventKey(), new CreateGameRequest(name, isRainbow));
    }

    @Override
    public void enterGame(String name, int gameId) {
        mSocket.emit(SocketEvent.ENTER_GAME.getEventKey(), new GameIdAndNameRequest(name, gameId));
    }

    @Override
    public void resumeGame(String name, int gameId) {
        mSocket.emit(SocketEvent.RESUME_GAME.getEventKey(), new GameIdAndNameRequest(name, gameId));
    }

    @Override
    public void joinGame(String name, int gameId) {
        mSocket.emit(SocketEvent.JOIN_GAME.getEventKey(), new GameIdAndNameRequest(name, gameId));
    }

    @Override
    public void startGame(int gameId) {
        mSocket.emit(SocketEvent.START_GAME.getEventKey(), new GameIdAndNameRequest(null, gameId));
    }

    @Override
    public void sendMessage(String name, int gameId, String message) {
        mSocket.emit(SocketEvent.SEND_MESSAGE.getEventKey(), new SendMessageRequest(name, gameId, message));
    }

    @Override
    public void giveColorHint(String hinter, int gameId, Suit suit, String hintee) {
        mSocket.emit(SocketEvent.GIVE_HINT.getEventKey(), new GiveColorHintRequest(hinter, gameId, hintee, suit));
    }

    @Override
    public void giveNumberHint(String hinter, int gameId, int num, String hintee) {
        mSocket.emit(SocketEvent.GIVE_HINT.getEventKey(), new GiveNumberHintRequest(hinter, gameId, hintee, num));
    }

    @Override
    public void discardCard(String name, int gameId, int cardIndex) {
        mSocket.emit(SocketEvent.DISCARD_CARD.getEventKey(), new CardRequest(name, gameId, cardIndex));
    }

    @Override
    public void playCard(String name, int gameId, int cardIndex) {
        mSocket.emit(SocketEvent.PLAY_CARD.getEventKey(), new CardRequest(name, gameId, cardIndex));
    }

    @Override
    public void endGame(String name, int gameId) {
        mSocket.emit(SocketEvent.END_GAME.getEventKey(), new GameIdAndNameRequest(null, gameId));
    }
}
