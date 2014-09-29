package com.ckroetsch.hanabi.app;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ckroetsch.hanabi.R;
import com.ckroetsch.hanabi.model.Card;
import com.ckroetsch.hanabi.model.Game;
import com.ckroetsch.hanabi.model.GameResponse;
import com.ckroetsch.hanabi.model.Player;
import com.ckroetsch.hanabi.network.HanabiFrontEndAPI;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import java.io.IOException;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import roboguice.fragment.RoboFragment;
import roboguice.inject.InjectView;

/**
 * @author curtiskroetsch
 */
public class GameFragment extends RoboFragment {

    public static final String KEY_GAME = "game";
    public static final String KEY_NAME = "name";

    private static final String TAG = GameFragment.class.getName();

    @InjectView(R.id.players)
    ListView mPlayers;

    @InjectView(R.id.board_container)
    LinearLayout mBoard;

    @InjectView(R.id.button_join)
    Button mJoinButton;

    @InjectView(R.id.button_start)
    Button mStartButton;

    @Inject
    HanabiFrontEndAPI mHanabiAPI;

    Game mGame;

    String mName;

    Handler mHander = new Handler();

    public static GameFragment createInstance(Game game, String name) {
        final GameFragment fragment = new GameFragment();
        final ObjectMapper mapper = new ObjectMapper();
        final Bundle args = new Bundle();
        args.putString(KEY_NAME, name);
        try {
            String gameJSON = mapper.writeValueAsString(game);
            Log.d(TAG, "gameJSON = " + gameJSON);
            args.putString(KEY_GAME, gameJSON);
            fragment.setArguments(args);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Could not write JSON");
        }
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final String gameJSON = getArguments().getString(KEY_GAME);
        mName = getArguments().getString(KEY_NAME);
        final ObjectMapper objectMapper = new ObjectMapper();
        try {
            Log.d(TAG, "gameJSON = " + gameJSON);
            final Game game = objectMapper.readValue(gameJSON, Game.class);
            Log.d(TAG, "gameJSON = " + game);
            mGame = game;
            mHander.post(new Runnable() {
                @Override
                public void run() {
                    bindGame(mGame);
                }
            });
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    private void bindCard(LayoutInflater inflater, Card card) {
        final View cardView = inflater.inflate(R.layout.view_card, mBoard, false);
        final TextView cardValue = (TextView) cardView.findViewById(R.id.card_number);
        cardValue.setText((card.getValue() == 0) ? "X" : "" + card.getValue());
        cardValue.setBackgroundResource(card.getSuit().getColorId());
        mBoard.addView(cardView);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mJoinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mHanabiAPI.join(mGame.getId(), mName, new Callback<GameResponse>() {
                    @Override
                    public void success(GameResponse gameResponse, Response response) {
                        if (gameResponse.isSuccess()) {
                            mJoinButton.setVisibility(View.INVISIBLE);
                            mStartButton.setVisibility(View.VISIBLE);
                            mGame = gameResponse.getGame();
                            bindGame(mGame);
                        } else {
                            Log.e(TAG, "join failed.");
                            Toast.makeText(getActivity(), "Could not join game", Toast.LENGTH_SHORT).show();
                            mJoinButton.setVisibility(View.INVISIBLE);
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.e(TAG, "join error: " + error.getMessage());
                        Toast.makeText(getActivity(), "Could not join game", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mHanabiAPI.start(mGame.getId(), new Callback<GameResponse>() {
                    @Override
                    public void success(GameResponse gameResponse, Response response) {
                        if (gameResponse.isSuccess()) {
                            Log.e(TAG, "start success");
                            mGame = gameResponse.getGame();
                            bindGame(mGame);
                            mStartButton.setVisibility(View.GONE);
                        } else {
                            Log.e(TAG, "start failed");
                            Toast.makeText(getActivity(), "Could not start game", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.e(TAG, "start error: " + error.getMessage());
                        Toast.makeText(getActivity(), "Could not start game", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void bindGame(Game game) {
        bindBoard(game);
        Log.d(TAG, "mPlayers: " + mPlayers);
        mPlayers.setAdapter(new PlayersAdapter(getActivity(), game.getPlayers()));
    }

    private void bindBoard(Game game) {
        final LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        for (Card card : game.getState()) {
            bindCard(inflater, card);
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
