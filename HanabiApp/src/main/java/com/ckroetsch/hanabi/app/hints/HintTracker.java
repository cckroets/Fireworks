package com.ckroetsch.hanabi.app.hints;

import com.ckroetsch.hanabi.events.socket.play.DiscardEvent;
import com.ckroetsch.hanabi.events.socket.play.GiveHintEvent;
import com.ckroetsch.hanabi.events.socket.play.PlayCardEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * @author curtiskroetsch
 */
public class HintTracker {

    final String mName;

    final List<List<GiveHintEvent>> mHints;

    public HintTracker(String name, int numCards) {
        mName = name;
        mHints = new ArrayList<List<GiveHintEvent>>();
        for (int i = 0; i < numCards; i++) {
            mHints.add(null);
        }
    }

    public void onHintGiven(GiveHintEvent event) {
        if (!event.name.equals(mName)) {
            return;
        }
        for (Integer i : event.cardsHinted) {
            List<GiveHintEvent> hintsForIndex = mHints.get(i);
            if (hintsForIndex == null) {
                hintsForIndex = new ArrayList<GiveHintEvent>();
                mHints.set(i, hintsForIndex);
            }
            hintsForIndex.add(event);
        }
    }

    public void onPlayCard(PlayCardEvent event) {
        if (!event.name.equals(mName)) {
            return;
        }

    }

    public void onDiscardCard(DiscardEvent event) {
        if (!event.name.equals(mName)) {
            return;
        }

    }

}
