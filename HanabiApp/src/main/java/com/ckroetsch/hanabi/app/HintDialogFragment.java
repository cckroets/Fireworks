package com.ckroetsch.hanabi.app;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.ckroetsch.hanabi.R;
import com.ckroetsch.hanabi.model.Card;
import com.ckroetsch.hanabi.model.Player;
import com.ckroetsch.hanabi.model.Suit;
import com.ckroetsch.hanabi.network.HanabiFrontEndAPI;
import com.ckroetsch.hanabi.view.ColorHintView;
import com.ckroetsch.hanabi.view.FakeListView;
import com.ckroetsch.hanabi.view.NumberHintView;
import com.ckroetsch.hanabi.view.Selectable;
import com.google.inject.Inject;

import java.util.HashSet;
import java.util.Set;

import roboguice.fragment.RoboDialogFragment;
import roboguice.inject.InjectView;

/**
 * @author curtiskroetsch
 */
public class HintDialogFragment extends RoboDialogFragment {

    final static String KEY_PLAYER = "player";
    final static String KEY_NAME = "name";
    final static String KEY_GAME_ID = "game_id";
    final static Integer[] NUMBERS = new Integer[]{1, 2, 3, 4, 5};
    final static Suit[] SUITS = new Suit[]{Suit.BLUE, Suit.GREEN, Suit.RED, Suit.WHITE, Suit.YELLOW};
    final static float DISABLED_ALPHA = 0.3f;

    Player mPlayer;

    String mName;

    int mGameId;

    Boolean[] mCardNumbers;

    Set<Suit> mSuitSet;

    @InjectView(R.id.colorList)
    FakeListView mColorListView;

    @InjectView(R.id.numberList)
    FakeListView mNumberListView;

    @InjectView(R.id.confirm)
    View mConfirm;

    @InjectView(R.id.cancel)
    View mCancel;

    @Inject
    LayoutInflater mInflater;

    @Inject
    HanabiFrontEndAPI mHanabiAPI;

    Selectable mSelected;

    Hint mSelectedHint;


    public static HintDialogFragment create(String name, int gameId, Player hintee) {
        final HintDialogFragment fragment = new HintDialogFragment();
        final Bundle args = new Bundle();
        args.putParcelable(KEY_PLAYER, hintee);
        args.putString(KEY_NAME, name);
        args.putInt(KEY_GAME_ID, gameId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPlayer = getArguments().getParcelable(KEY_PLAYER);
        mName = getArguments().getString(KEY_NAME);
        mGameId = getArguments().getInt(KEY_GAME_ID);
        mSuitSet = new HashSet<Suit>();
        mCardNumbers = new Boolean[5];
        for (int i = 0; i < mCardNumbers.length; i++) {
            mCardNumbers[i] = Boolean.FALSE;
        }

        for (Card card : mPlayer.getHand()) {
            mCardNumbers[card.getNumber() - 1] = true;
            mSuitSet.add(card.getSuit());
        }
    }

    private boolean isSuitPresent(Suit suit) {
        return mSuitSet.contains(Suit.RAINBOW) || mSuitSet.contains(suit);
    }

    private boolean isNumberPresent(int number) {
        return mCardNumbers[number - 1];
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hint, null, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        mColorListView.setAdapter(new SuitAdapter(getActivity()));
        mNumberListView.setAdapter(new NumberAdapter(getActivity()));
        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onConfirm();
            }
        });
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCancel();
            }
        });
    }

    private void onConfirm() {
        if (mSelectedHint != null) {
            mSelectedHint.giveHint();
        }
        getDialog().dismiss();
    }

    private void onCancel() {
        getDialog().cancel();
    }

    private void setEnabledView(View view, boolean enabled) {
        view.setAlpha(enabled ? 1f : DISABLED_ALPHA);
        view.setEnabled(enabled);
    }

    private void onViewSelected(Selectable view) {
        if (view == mSelected) {
            return;
        } else if (mSelected != null) {
            mSelected.onDeselect();
        }
        mSelected = view;
        mSelected.onSelect();
    }


    public class SuitAdapter extends HintAdapter<Suit> {

        public SuitAdapter(Context context) {
            super(context, SUITS);
        }

        @Override
        public Hint getHint(Suit item) {
            return new SuitHint(item);
        }

        @Override
        public boolean isItemEnabled(Suit item) {
            return isSuitPresent(item);
        }

        @Override
        public View createView(int position) {
            return mInflater.inflate(R.layout.view_color_hint, null);
        }

        @Override
        public Selectable getSelectable(View view) {
            return (Selectable) view;
        }

        @Override
        public View getView(int position, Suit suit) {
            final ColorHintView view = (ColorHintView) super.getView(position, suit);
            view.bindWithSuit(suit, isItemEnabled(suit));
            return view;
        }
    }

    public class NumberAdapter extends HintAdapter<Integer> {

        @Override
        public Hint getHint(Integer hint) {
            return new NumberHint(hint);
        }

        @Override
        public boolean isItemEnabled(Integer item) {
            return isNumberPresent(item);
        }

        @Override
        public View createView(int position) {
            return mInflater.inflate(R.layout.view_number_hint, null);
        }

        @Override
        public Selectable getSelectable(View view) {
            return (Selectable) view;
        }

        public NumberAdapter(Context context) {
            super(context, NUMBERS);
        }

        @Override
        protected View getView(int position, Integer number) {
            final NumberHintView view = (NumberHintView) super.getView(position, number);
            view.bindWithNumber(number);
            return view;
        }
    }


    public abstract class HintAdapter<T> extends FakeListView.FakeListAdapter<T> {

        View.OnClickListener mHintClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Hint value = (Hint) view.getTag();
                onItemSelected(view, value);
            }
        };

        public HintAdapter(Context context, T[] items) {
            super(context, items);
        }

        public abstract Hint getHint(T item);

        public abstract boolean isItemEnabled(T item);

        public abstract View createView(int position);

        public abstract Selectable getSelectable(View view);

        protected void onItemSelected(View view, Hint hint) {
            onViewSelected((Selectable)view);
            mSelectedHint = hint;
        }

        @Override
        protected View getView(int position, T item) {
            final View view = createView(position);
            final boolean enabled = isItemEnabled(item);
            getSelectable(view).onDeselect();
            view.setTag(getHint(item));
            view.setOnClickListener(mHintClickListener);
            setEnabledView(view, enabled);
            return view;
        }

    }

    public interface Hint {
        void giveHint();
    }

    public class NumberHint implements Hint {
        int mNumber;

        public NumberHint(int num) {
            mNumber = num;
        }

        @Override
        public void giveHint() {
            mHanabiAPI.giveNumberHint(mName, mGameId,  mNumber, mPlayer.getName());
        }
    }

    public class SuitHint implements Hint {

        Suit mSuit;

        public SuitHint(Suit suit) {
            mSuit = suit;
        }

        @Override
        public void giveHint() {
            mHanabiAPI.giveColorHint(mName, mGameId, mSuit, mPlayer.getName());
        }
    }
}
