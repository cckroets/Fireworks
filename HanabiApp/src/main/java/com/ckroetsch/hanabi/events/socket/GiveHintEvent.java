package com.ckroetsch.hanabi.events.socket;

import com.ckroetsch.hanabi.model.Game;

/**
 * @author curtiskroetsch
 */
public class GiveHintEvent {
    public static String SUIT = "color";
    public static String NUMBER = "number";

    public Game game;
    public String hintType;
    public String name;
    public String toName;
    public String hint;
    public String cardsHinted;
}
