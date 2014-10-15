package com.ckroetsch.hanabi.app;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ckroetsch.hanabi.R;
import com.ckroetsch.hanabi.model.Card;
import com.ckroetsch.hanabi.model.Player;
import com.ckroetsch.hanabi.model.Suit;
import com.ckroetsch.hanabi.view.FakeListView;

import java.util.HashSet;
import java.util.Set;

import roboguice.fragment.RoboDialogFragment;
import roboguice.inject.InjectView;

/**
 * @author curtiskroetsch
 */
public class HintDialogFragment extends RoboDialogFragment {

    final static String KEY_PLAYER = "player";
    final static Integer[] NUMBERS = new Integer[] { 1, 2, 3, 4, 5 };
    final static Suit[] SUITS = new Suit[] { Suit.BLUE, Suit.GREEN, Suit.RED, Suit.WHITE, Suit.YELLOW };
    final static float DISABLED_ALPHA = 0.3f;

    Player mPlayer;

    Boolean[] mCardNumbers;

    Set<Suit> mSuitSet;

    @InjectView(R.id.colorList)
    FakeListView mColorListView;

    @InjectView(R.id.numberList)
    FakeListView mNumberListView;

    public static HintDialogFragment create(Player hintee) {
        final HintDialogFragment fragment = new HintDialogFragment();
        final Bundle args = new Bundle();
        args.putParcelable(KEY_PLAYER, hintee);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPlayer = getArguments().getParcelable(KEY_PLAYER);
        mSuitSet = new HashSet<Suit>();
        mCardNumbers = new Boolean[mPlayer.getHand().size()];
        for (int i = 0; i < mCardNumbers.length; i++) {
            mCardNumbers[i] = Boolean.FALSE;
        }

        for (Card card : mPlayer.getHand()) {
            mCardNumbers[card.getValue() - 1] = true;
            mSuitSet.add(card.getSuit());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hint, null, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mColorListView.setAdapter(new SuitAdapter(getActivity()));
        mNumberListView.setAdapter(new NumberAdapter(getActivity()));
    }

    private void giveNumberHint(int number) {

    }

    private void giveSuitHint(Suit suit) {

    }


    public class SuitAdapter extends ArrayAdapter<Suit> {

        View.OnClickListener mSuitClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Suit value = (Suit) view.getTag();
                giveSuitHint(value);
            }
        };

        public SuitAdapter(Context context) {
            super(context, 0, SUITS);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final View view = new TextView(getContext());
            final Suit suit = getItem(position);

            view.setBackgroundResource(suit.getColorId());
            view.setTag(suit);

            if (mSuitSet.contains(suit)) {
                view.setOnClickListener(mSuitClickListener);
            } else {
                view.setAlpha(DISABLED_ALPHA);
            }

            return view;
        }


    }

    public class NumberAdapter extends ArrayAdapter<Integer> {

        View.OnClickListener mNumberClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Integer value = (Integer) view.getTag();
                giveNumberHint(value);
            }
        };

        public NumberAdapter(Context context) {
            super(context, 0, NUMBERS);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final TextView view = new TextView(getContext());
            view.setText("" + (position + 1));
            view.setTextColor(getResources().getColor(android.R.color.white));
            view.setTag(position + 1);

            if (mCardNumbers[position]) {
                view.setEnabled(true);
                view.setOnClickListener(mNumberClickListener);
            } else {
                view.setEnabled(false);
                view.setAlpha(DISABLED_ALPHA);
            }

            return view;
        }
    }
}
