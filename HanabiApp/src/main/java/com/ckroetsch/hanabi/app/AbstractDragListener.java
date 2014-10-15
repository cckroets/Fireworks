package com.ckroetsch.hanabi.app;

import android.view.DragEvent;
import android.view.View;

/**
 * @author curtiskroetsch
 */
public class AbstractDragListener implements View.OnDragListener {

    public AbstractDragListener() {

    }


    @Override
    public boolean onDrag(View view, DragEvent dragEvent) {
        return false;
    }
}
