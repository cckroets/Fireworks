package com.ckroetsch.hanabi.events.game;

import com.ckroetsch.hanabi.model.Suit;

/**
 * @author curtiskroetsch
 */
public class NumberHintEvent implements HintEvent {

    String mHinter;
    String mHintee;
    int mNumber;

    public NumberHintEvent(String hinter, String hintee, int number) {
        mHintee = hintee;
        mHinter = hinter;
        mNumber = number;
    }

    public String getHinter() {
        return mHinter;
    }

    public String getHintee() {
        return mHintee;
    }

    public int getNumber() {
        return mNumber;
    }
}
