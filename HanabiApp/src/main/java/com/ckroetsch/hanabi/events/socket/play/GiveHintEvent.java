package com.ckroetsch.hanabi.events.socket.play;

import com.ckroetsch.hanabi.events.socket.GameChangeEvent;
import com.ckroetsch.hanabi.model.Game;

import java.util.List;

/**
 * @author curtiskroetsch
 */
public class GiveHintEvent extends GameChangeEvent {
    public static String SUIT = "color";
    public static String NUMBER = "number";

    public String hintType;
    public String from;
    public String to;
    public String hint;
    public List<Integer> cardsHinted;

    @Override
    public String getName() {
        return from;
    }
}
