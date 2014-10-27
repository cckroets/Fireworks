package com.ckroetsch.hanabi.model;

import com.ckroetsch.hanabi.R;

/**
 * @author curtiskroetsch
 */
public enum Suit {
    WHITE(R.color.pastel_white),
    RED(R.color.pastel_red),
    BLUE(R.color.pastel_blue),
    GREEN(R.color.pastel_green),
    RAINBOW(R.drawable.rainbow2),
    YELLOW(R.color.pastel_yellow);

    private int color;

    Suit(int c) {
        this.color = c;
    }

    public int getColorId() {
        return color;
    }
}
