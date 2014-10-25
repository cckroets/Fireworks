package com.ckroetsch.hanabi.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.ckroetsch.hanabi.R;

/**
 * @author curtiskroetsch
 */
public class NumberHintView extends TextView implements Selectable {

    public NumberHintView(Context context) {
        super(context);
    }

    public NumberHintView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NumberHintView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void bindWithNumber(Integer number) {
        if (number != null) {
            setText(number.toString());
        }
    }

    @Override
    public void onDeselect() {
        setBackgroundResource(0);
    }

    @Override
    public void onSelect() {
        setBackgroundResource(R.drawable.border);
    }
}
