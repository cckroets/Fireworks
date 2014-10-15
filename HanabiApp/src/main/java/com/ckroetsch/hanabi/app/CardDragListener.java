package com.ckroetsch.hanabi.app;

import android.view.DragEvent;
import android.view.View;

/**
* @author curtiskroetsch
*/
class CardDragListener implements View.OnDragListener {

    final String mName;
    final int mIndex;

    public CardDragListener(String player, int cardIndex) {
        mName = player;
        mIndex = cardIndex;
    }

    private boolean isMyDragEvent(DragEvent dragEvent) {
        GameFragment.CardState state = (GameFragment.CardState) dragEvent.getLocalState();
        return mIndex == state.index && mName.equals(state.player);
    }


    @Override
    public boolean onDrag(View view, DragEvent dragEvent) {
        if (!isMyDragEvent(dragEvent)) {
            return false;
        }

        switch (dragEvent.getAction()) {
            case DragEvent.ACTION_DRAG_STARTED:
                view.setVisibility(View.INVISIBLE);
                break;
            case DragEvent.ACTION_DRAG_ENTERED:
            case DragEvent.ACTION_DRAG_LOCATION:
            case DragEvent.ACTION_DRAG_EXITED:
                break;
            case DragEvent.ACTION_DROP:
            case DragEvent.ACTION_DRAG_ENDED:
                if (!dragEvent.getResult()) {
                    view.setVisibility(View.VISIBLE);
                }
                break;
        }
        return true;
    }
}
