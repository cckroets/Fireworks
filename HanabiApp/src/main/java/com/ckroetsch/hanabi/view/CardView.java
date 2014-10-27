package com.ckroetsch.hanabi.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ckroetsch.hanabi.R;
import com.ckroetsch.hanabi.events.socket.play.GiveHintEvent;
import com.ckroetsch.hanabi.model.Card;

import java.util.List;

/**
 * @author curtiskroetsch
 */
public class CardView extends FrameLayout {

    private static final int HINT_WEIGHT = 1;
    private static LinearLayout.LayoutParams HINT_PARAMS = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
    static {
        HINT_PARAMS.weight = HINT_WEIGHT;
    }

    LinearLayout mHintContainer;

    TextView mNumberText;

    public CardView(Context context) {
        super(context);
    }

    public CardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setText(CharSequence text) {
        mNumberText.setText(text);
    }

    public void setTextSize(int unit, float value) {
        mNumberText.setTextSize(unit, value);
    }

    public void bindWithCard(Card card) {
        setText(Integer.toString(card.getValue()));
        setBackgroundResource(card.getSuit().getColorId());
    }

    public void bindWithUnknown(List<GiveHintEvent> hints) {
        setText(getResources().getString(R.string.unkown_card));
        setBackgroundResource(R.color.table_center);
        if (hints == null) {
            return;
        }
        mHintContainer.removeAllViews();
        for (GiveHintEvent event : hints) {
            if (event.hintType.equals(GiveHintEvent.SUIT)) {
                View view = new View(getContext());
                mHintContainer.addView(view, HINT_PARAMS);
            } else {
                setText(event.hint);
            }
        }
    }
}
