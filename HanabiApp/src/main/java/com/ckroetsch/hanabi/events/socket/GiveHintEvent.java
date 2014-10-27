package com.ckroetsch.hanabi.events.socket;

import com.ckroetsch.hanabi.model.Game;

import java.util.List;

/**
 * @author curtiskroetsch
 */
public class GiveHintEvent {
    public static String SUIT = "color";
    public static String NUMBER = "number";

    public Game game;
    public String hintType;
    public String from;
    public String to;
    public String hint;
    public List<Integer> cardsHinted;
}
