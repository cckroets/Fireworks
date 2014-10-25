package com.ckroetsch.hanabi.events.game;

import com.ckroetsch.hanabi.model.Suit;

/**
 * @author curtiskroetsch
 */
public class SuitHintEvent implements HintEvent {

    String mHinter;
    String mHintee;
    Suit mSuit;

    public SuitHintEvent(String hinter, String hintee, Suit suit) {
        mHintee = hintee;
        mHinter = hinter;
        mSuit = suit;
    }

    public String getHinter() {
        return mHinter;
    }

    public String getHintee() {
        return mHintee;
    }

    public Suit getSuit() {
        return mSuit;
    }


}
