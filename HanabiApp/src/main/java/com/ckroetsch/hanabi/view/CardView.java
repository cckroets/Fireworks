package com.ckroetsch.hanabi.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ckroetsch.hanabi.R;
import com.ckroetsch.hanabi.model.Card;
import com.google.inject.Inject;

/**
 * @author curtiskroetsch
 */
public class CardView extends TextView {

    public CardView(Context context) {
        super(context);
    }

    public CardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void bindWithCard(Card card) {
        setText(card.getValue() + "");
        setBackgroundResource(card.getSuit().getColorId());
    }

    public void bindWithUnknown() {
        setText(null);
        setBackgroundResource(R.color.table_center);
    }
}
