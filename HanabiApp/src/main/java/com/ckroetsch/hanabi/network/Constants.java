package com.ckroetsch.hanabi.network;

/**
 * @author curtiskroetsch
 */
public final class Constants {

    public static final String SOCKET_URL_PROD = "http://murmuring-caverns-8259.herokuapp.com/";
    public static final String SOCKET_URL_TEST = "http://192.168.2.7:5000/";

    public static final String API_URL_PROD = "http://murmuring-caverns-8259.herokuapp.com/api";
    public static final String API_URL_TEST = "http://192.168.2.7:5000/api";

    public static final String API_URL = API_URL_PROD;

    public static final String SOCKET_URL = SOCKET_URL_PROD;

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
