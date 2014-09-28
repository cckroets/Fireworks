package com.ckroetsch.hanabi.app;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ckroetsch.hanabi.R;
import com.ckroetsch.hanabi.model.Card;
import com.ckroetsch.hanabi.model.Game;
import com.ckroetsch.hanabi.model.Player;
import com.ckroetsch.hanabi.model.Suit;
import com.ckroetsch.hanabi.network.HanabiFrontEndAPI;
import com.google.inject.Inject;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import roboguice.fragment.RoboFragment;
import roboguice.inject.InjectView;

/**
 * @author curtiskroetsch
 */
public class MainFragment extends RoboFragment {

    private static final String TAG = MainFragment.class.getName();

    @InjectView(R.id.players)
    ListView mPlayers;

    @InjectView(R.id.board_container)
    LinearLayout mBoard;

    @InjectView(R.id.board_blue)
    View mBlue;

    @InjectView(R.id.board_red)
    View mRed;

    @InjectView(R.id.board_yellow)
    View mYellow;

    @InjectView(R.id.board_green)
    View mGreen;

    @InjectView(R.id.board_white)
    View mWhite;

    @InjectView(R.id.board_rainbow)
    View mRainbow;

    @Inject
    HanabiFrontEndAPI mHanabiAPI;

    Handler mHander = new Handler();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHanabiAPI.getGame(4, new Callback<Game>() {
            @Override
            public void success(final Game game, Response response) {
                mHander.post(new Runnable() {
                    @Override
                    public void run() {
                        bindGame(game);
                    }
                });
            }
            @Override
            public void failure(RetrofitError error) {
                Log.e(TAG, "error: " + error.getMessage());
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    private void bindCard(View cardView, int colorId, int value) {
        final TextView cardValue = (TextView) cardView.findViewById(R.id.card_number);
        cardValue.setText((value == 0) ? "X" : "" + value);
        cardValue.setBackgroundResource(colorId);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void bindGame(Game game) {
        bindBoard(game);
        mPlayers.setAdapter(new PlayersAdapter(getActivity(), game.getPlayers()));
    }

    private void bindBoard(Game game) {
        bindCard(mWhite, Suit.WHITE.getColorId(), game.getState().getWhite());
        bindCard(mRed, Suit.RED.getColorId(), game.getState().getRed());
        bindCard(mGreen, Suit.GREEN.getColorId(), game.getState().getGreen());
        bindCard(mYellow, Suit.YELLOW.getColorId(), game.getState().getYellow());
        bindCard(mBlue, Suit.BLUE.getColorId(), game.getState().getBlue());
        if (game.isRainbow()) {
            bindCard(mRainbow, Suit.RAINBOW.getColorId(), game.getState().getRainbow());
        } else {
            mRainbow.setVisibility(View.GONE);
        }
    }


    class PlayersAdapter extends ArrayAdapter<Player> {

        public PlayersAdapter(Context context, List<Player> players) {
            super(context, R.layout.view_hand, players);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            final LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            ViewHolder holder;

            if (convertView == null || convertView.getTag() == null) {
                convertView = inflater.inflate(R.layout.view_hand, null);
                holder = new ViewHolder();
                holder.name = (TextView) convertView.findViewById(R.id.hand_name);
                holder.cardContainer = (LinearLayout) convertView.findViewById(R.id.hand_container);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            final Player player = getItem(position);

            holder.name.setText(player.getName());
            holder.cardContainer.removeAllViews();
            for (Card card : player.getHand()) {
                final View cardView = inflater.inflate(R.layout.view_card, holder.cardContainer, false);
                TextView cardNumber = (TextView) cardView.findViewById(R.id.card_number);
                cardNumber.setText("" + card.getValue());
                cardNumber.setBackgroundResource(card.getSuit().getColorId());
                holder.cardContainer.addView(cardView);
            }
            return convertView;
        }

        @Override
        public int getCount() {
            return super.getCount();
        }

        @Override
        public Player getItem(int position) {
            return super.getItem(position);
        }
    }

    class ViewHolder {
        TextView name;
        LinearLayout cardContainer;
    }
}
