package com.ckroetsch.hanabi.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.ckroetsch.hanabi.R;
import com.ckroetsch.hanabi.model.Suit;

/**
 * @author curtiskroetsch
 */
public class ColorHintView extends FrameLayout implements Selectable {

    View mDisabledView;
    View mColorView;

    public ColorHintView(Context context) {
        super(context);
    }

    public ColorHintView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ColorHintView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setClickable(true);
        mDisabledView = findViewById(R.id.disabled);
        mColorView = findViewById(R.id.color);
    }

    public void bindWithSuit(Suit suit, boolean enabled) {
        setEnabled(enabled);
        mColorView.setBackgroundResource(suit.getColorId());
        mDisabledView.setVisibility(enabled ? GONE : VISIBLE);
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
