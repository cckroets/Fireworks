package com.ckroetsch.hanabi.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ckroetsch.hanabi.R;
import com.ckroetsch.hanabi.model.Card;
import com.ckroetsch.hanabi.model.Suit;

import java.util.HashSet;
import java.util.Set;

/**
 * @author curtiskroetsch
 */
public class CardView extends FrameLayout {

    public enum Size {
        SMALL(21f, 28f, 5f, 20),
        MEDIUM(30f, 40f, 5f, 28),
        LARGE(48f, 64f, 5f, 48);

        public final float width;
        public final float height;
        public final float margin;
        public final int textSize;

        private Size(float width, float height, float margin, int textSize) {
            this.width = width;
            this.height = height;
            this.margin = margin;
            this.textSize = textSize;
        }
    }

    private static final String TAG = CardView.class.getName();
    private static final int HINT_WEIGHT = 1;
    private static LinearLayout.LayoutParams HINT_PARAMS = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
    static {
        HINT_PARAMS.weight = HINT_WEIGHT;
    }

    LinearLayout mHintContainer;

    TextView mNumberView;

    public CardView(Context context) {
        super(context);
    }

    public CardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public static CardView createCard(Activity activity, LayoutInflater inflater, ViewGroup container, Size size) {
        final CardView cardView = (CardView) inflater.inflate(R.layout.view_card, container, false);
        LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        llp.width = (int) convertDpToPixel(activity, size.width);
        llp.height = (int) convertDpToPixel(activity, size.height);
        final int margin = (int) convertDpToPixel(activity, size.margin);
        llp.setMargins(margin, margin, margin, margin);
        cardView.setLayoutParams(llp);
        cardView.setTextSize(TypedValue.COMPLEX_UNIT_SP, size.textSize);
        return cardView;
    }

    public static float convertDpToPixel(Activity activity, float dp) {
        return dp * activity.getResources().getDisplayMetrics().density;
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mHintContainer = (LinearLayout) findViewById(R.id.card_hints);
        mNumberView = (TextView) findViewById(R.id.card_number);
    }

    public void setTextSize(int unit, float value) {
        mNumberView.setTextSize(unit, value);
    }

    public void bindWithCard(Card card) {
        mNumberView.setTextColor(getResources().getColor(R.color.black));
        mNumberView.setText(Integer.toString(card.getNumber()));
        mNumberView.setBackgroundResource(card.getSuit().getColorId());
    }

    public void bindWithUnknown(Card card) {
        mNumberView.setTextColor(getResources().getColor(R.color.pastel_white));
        mNumberView.setText(getResources().getString(R.string.unknown_card));
        setBackgroundResource(R.color.table_center);
        mHintContainer.removeAllViews();
        if (card.getKnowNumber()) {
            mNumberView.setText(Integer.toString(card.getNumber()));
        }
        final Set<String> suitsHinted = new HashSet<String>();
        for (Suit suit : card.getKnownSuit()) {
            if (suitsHinted.contains(suit.name())) {
                continue;
            }
            suitsHinted.add(suit.name());
            View view = new View(getContext());
            view.setBackgroundResource(suit.getColorId());
            mHintContainer.addView(view, HINT_PARAMS);
        }
    }
}
