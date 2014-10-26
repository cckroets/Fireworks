package com.ckroetsch.hanabi.app;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.ClipData;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ckroetsch.hanabi.R;
import com.ckroetsch.hanabi.events.game.CardDiscardedEvent;
import com.ckroetsch.hanabi.events.socket.DiscardEvent;
import com.ckroetsch.hanabi.events.socket.HanabiErrorEvent;
import com.ckroetsch.hanabi.events.socket.JoinGameEvent;
import com.ckroetsch.hanabi.events.socket.PlayCardEvent;
import com.ckroetsch.hanabi.events.socket.SocketEvent;
import com.ckroetsch.hanabi.events.socket.StartGameEvent;
import com.ckroetsch.hanabi.model.Card;
import com.ckroetsch.hanabi.model.Game;
import com.ckroetsch.hanabi.model.GameResponse;
import com.ckroetsch.hanabi.model.Player;
import com.ckroetsch.hanabi.network.HanabiError;
import com.ckroetsch.hanabi.network.HanabiFrontEndAPI;
import com.ckroetsch.hanabi.network.HanabiRetrofitFrontEndAPI;
import com.ckroetsch.hanabi.network.HanabiSocketIO;
import com.ckroetsch.hanabi.util.JsonUtil;
import com.ckroetsch.hanabi.view.CardView;
import com.ckroetsch.hanabi.view.FakeListView;
import com.google.inject.Inject;
import com.squareup.otto.Subscribe;

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

    @InjectView(R.id.discard)
    View mDiscard;

    @Inject
    LayoutInflater mInflater;

    @Inject
    HanabiFrontEndAPI mHanabiAPI;

    Game mGame;

    String mName;

    Handler mHandler = new Handler();


    public static GameFragment createInstance(Bundle args) {
        final GameFragment fragment = new GameFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final String gameJSON = getArguments().getString(KEY_GAME);
        mName = getArguments().getString(KEY_NAME);
        HanabiSocketIO socketIo = new HanabiSocketIO();
        socketIo.connect();
        Log.d(TAG, "gameJSON = " + gameJSON);
        final Game game = JsonUtil.jsonToObject(gameJSON, Game.class);
        Log.d(TAG, "gameJSON = " + game);
        mGame = game;
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                bindGame(mGame);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_game, container, false);
    }

    @Subscribe
    private void onJoin(JoinGameEvent event) {
        Log.e(TAG, "join success");
        mJoinButton.setVisibility(View.INVISIBLE);
        mStartButton.setVisibility(View.VISIBLE);
        mGame = event.game;
        bindGame(mGame);
    }

    private void onJoinFailed(HanabiError error) {
        Log.e(TAG, "join error: " + error.getReason());
        Toast.makeText(getActivity(), error.getReason(), Toast.LENGTH_SHORT).show();
        mJoinButton.setVisibility(View.GONE);
    }

    @Subscribe
    private void onStart(StartGameEvent event) {
        Log.e(TAG, "start success");
        mStartButton.setVisibility(View.GONE);
        Toast.makeText(getActivity(), "Started", Toast.LENGTH_SHORT).show();
        mGame = event.game;
        bindGame(mGame);
    }

    private void onStartFailed(HanabiError error) {
        Log.e(TAG, "start error: " + error.getReason());
        Toast.makeText(getActivity(), error.getReason(), Toast.LENGTH_SHORT).show();
    }

    @Subscribe
    private void onDiscard(DiscardEvent event) {
        Log.d(TAG, "successfully discarded " + event.cardIndex);
        Toast.makeText(getActivity(), "Discarded: " + event.cardIndex, Toast.LENGTH_SHORT).show();
        mGame = event.game;
        bindGame(mGame);
    }

    @Subscribe
    private void onPlay(PlayCardEvent event) {
        Log.d(TAG, "successfully played " + event.cardIndex);
        Toast.makeText(getActivity(), "Played: " + event.cardIndex, Toast.LENGTH_SHORT).show();
        mGame = event.game;
        bindGame(mGame);
    }

    private void onPlayFailed(HanabiError error) {
        Log.e(TAG, "play or discard error: " + error.getReason());
        Toast.makeText(getActivity(), error.getReason(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mGame.hasStarted()) {
            mJoinButton.setVisibility(View.GONE);
            mStartButton.setVisibility(View.GONE);
        }
        mJoinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mHanabiAPI.joinGame(mName, mGame.getId());
            }
        });
        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mHanabiAPI.startGame(mGame.getId());
            }
        });
        mDiscard.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View view, DragEvent dragEvent) {
                if (dragEvent.getAction() == DragEvent.ACTION_DRAG_STARTED) {
                    view.setTranslationY(200f);
                    view.setAlpha(0f);
                    view.animate().translationY(0f).alpha(1f).start();
                    return true;
                } else if (dragEvent.getAction() == DragEvent.ACTION_DROP) {
                    final CharSequence indexString = dragEvent.getClipData().getItemAt(0).coerceToText(getActivity());
                    final int index = Integer.parseInt(indexString.toString());
                    Log.d(TAG, "discarding " + index);
                    mHanabiAPI.discardCard(mName, mGame.getId(), index);
                    return true;
                } else if (dragEvent.getAction() == DragEvent.ACTION_DRAG_ENDED) {
                    view.animate().translationY(200f).alpha(0f).start();
                    return true;
                }
                return true;
            }
        });
        mBoard.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View view, DragEvent dragEvent) {
                if (dragEvent.getAction() == DragEvent.ACTION_DRAG_STARTED) {
                    return true;
                } else if (dragEvent.getAction() == DragEvent.ACTION_DROP) {
                    final CharSequence indexString = dragEvent.getClipData().getItemAt(0).coerceToText(getActivity());
                    final int index = Integer.parseInt(indexString.toString());
                    Log.d(TAG, "playing " + index);
                    mHanabiAPI.playCard(mName, mGame.getId(), index);
                    return true;
                } else if (dragEvent.getAction() == DragEvent.ACTION_DRAG_ENDED) {
                    return true;
                }
                return true;
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "onStart()");
        bindGame(mGame);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume()");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "onPause()");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG, "onStop()");
    }

    private void bindGame(Game game) {
        bindBoard(game);
        Log.d(TAG, "mPlayers: " + mPlayers);
        mPlayers.setAdapter(new PlayersAdapter(getActivity(), game.getPlayers()));
    }

    private void bindBoard(Game game) {
        mBoard.removeAllViews();
        for (Card card : game.getPlayed()) {
            bindCard(card);
        }
    }

    private void bindCard(Card card) {
        final CardView cardView = (CardView) mInflater.inflate(R.layout.view_card, mBoard, false);
        cardView.bindWithCard(card);
        mBoard.addView(cardView);
    }

    public float convertDpToPixel(float dp) {
        return dp * getActivity().getResources().getDisplayMetrics().density;
    }

    class PlayersAdapter extends FakeListView.FakeListAdapter<Player> {

        final LayoutInflater mInflater;

        public PlayersAdapter(Context context, List<Player> players) {
            super(context, players);
            mInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }


        @Override
        public View getView(int position, final Player player) {

            final View handView = mInflater.inflate(R.layout.view_hand, null);
            final TextView nameView = (TextView) handView.findViewById(R.id.hand_name);
            final LinearLayout cardContainer = (LinearLayout) handView.findViewById(R.id.hand_container);
            final View blinker = handView.findViewById(R.id.blinker);

            nameView.setText(player.getName());
            cardContainer.removeAllViews();
            boolean isMe = player.getName().equals(mName);
            if (player.getName().equals(mGame.getCurrentPlayerName())) {
                blinker.setAlpha(0f);
                blinker.setVisibility(View.VISIBLE);
                ObjectAnimator animator = ObjectAnimator.ofFloat(blinker, View.ALPHA, 0.05f);
                animator.setRepeatMode(ValueAnimator.REVERSE);
                animator.setRepeatCount(ValueAnimator.INFINITE);
                animator.setDuration(400);
                animator.start();
            } else {
                blinker.setVisibility(View.INVISIBLE);
            }

            handView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    HintDialogFragment.create(player).show(getFragmentManager(), null);
                }
            });

            int index = 0;
            for (final Card card : player.getHand()) {
                final int cardIndex = index;
                final CardView cardView = (CardView) mInflater.inflate(R.layout.view_card, cardContainer, false);
                LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                llp.width = (int) convertDpToPixel(48f);
                llp.height = (int) convertDpToPixel(64f);
                final int margin = (int) convertDpToPixel(5f);
                llp.setMargins(margin, margin, margin, margin);
                cardView.setLayoutParams(llp);
                if (isMe) {
                    cardView.bindWithCard(card);
                    //cardView.bindWithUnknown();
                } else {
                    cardView.bindWithCard(card);
                }
                if (isMe) {
                    cardView.setOnDragListener(new CardDragListener(player.getName(), cardIndex));
                    cardView.setOnTouchListener(new View.OnTouchListener() {

                        @Override
                        public boolean onTouch(View view, MotionEvent event) {
                            final ClipData dragData = ClipData.newPlainText("data", cardIndex + "");
                            final View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                            final CardState state = new CardState();
                            state.index = cardIndex;
                            state.player = player.getName();
                            state.card = card;
                            view.startDrag(dragData, shadowBuilder, state, 0);
                            return true;
                        }
                    });
                } else {
                    cardView.setClickable(false);
                }
                cardContainer.addView(cardView);
                index++;
            }
            return handView;
        }
    }

    @Subscribe
    void onFailure(HanabiErrorEvent event) {
        SocketEvent socketEvent = SocketEvent.getEvent(event.getError().getEvent());
        String eventString = event.getError().getEvent();
        Log.e(TAG, "onFailure() " + eventString + " : " + event.getError().getReason());
        if (socketEvent == null) {
            Log.e(TAG, "Unknown error event : " + eventString);
            return;
        }
        switch (socketEvent) {
            case JOIN_GAME:
                onJoinFailed(event.getError());
                break;
            case START_GAME:
                onStartFailed(event.getError());
                break;
            case PLAY_CARD:
            case DISCARD_CARD:
                onPlayFailed(event.getError());
            default:
                break;
        }
    }

    static class CardState {
        Card card;
        String player;
        int index;
    }
}
