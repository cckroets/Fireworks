package com.ckroetsch.hanabi.network;

/**
 * @author curtiskroetsch
 */
public final class Constants {

    public static final String API_URL = "https://timpei.com/api";

    // Start game
    public static final String API_CREATE = "/create";
    public static final String API_ENTER = "/enter/{id}";
    public static final String API_JOIN = "/join/{id}";
    public static final String API_START = "/start/{id}";

    // Play game
    public static final String API_GAME = "/game/{id}";
    public static final String API_HINT_COLOR = "/hint/color/{id}";
    public static final String API_HINT_NUMBER = "/hint/number/{id}";
    public static final String API_DISCARD = "/discard/{id}";
    public static final String API_PLAY = "/play/{id}";

    // Other
    public static final String API_MESSAGE = "/message/{id}";
    public static final String API_END = "/end/{id}";

    private Constants() {

    }

}
