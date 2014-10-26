package com.ckroetsch.hanabi.events.socket;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @author curtiskroetsch
 */
public enum SocketEvent {
    CREATE_GAME("createGame", null),
    ENTER_GAME("enterGame", EnterGameEvent.class),
    RESUME_GAME("resumeGame", ResumeGameEvent.class),
    JOIN_GAME("joinGame", JoinGameEvent.class),
    START_GAME("startGame", StartGameEvent.class),
    GIVE_HINT("giveHint", GiveHintEvent.class),
    DISCARD_CARD("discardCard", DiscardEvent.class),
    PLAY_CARD("playCard", PlayCardEvent.class),
    END_GAME("endGame", EndGameEvent.class),
    SEND_MESSAGE("sendMessage", SendMessageEvent.class);

    static final Map<String, SocketEvent> sNames = new HashMap<String, SocketEvent>();
    static {
        for (SocketEvent event : SocketEvent.values()) {
            sNames.put(event.mEventKey, event);
        }
    }

    public static SocketEvent getEvent(String name) {
        return sNames.get(name);
    }

    String mEventKey;
    Class mEventClass;

    private SocketEvent(String name, Class eventClass) {
        mEventKey = name;
        mEventClass = eventClass;
    }

    public Class getEventClass() {
        return mEventClass;
    }

    public String getEventKey() { return mEventKey; }

    public interface Event {
        void bindToJson(JSONObject object);
    }

}
