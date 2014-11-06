package com.ckroetsch.hanabi.app.chat;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.ckroetsch.hanabi.R;
import com.ckroetsch.hanabi.app.TabbedHanabiFragment;
import com.ckroetsch.hanabi.events.BusSingleton;
import com.ckroetsch.hanabi.events.socket.play.DiscardEvent;
import com.ckroetsch.hanabi.events.socket.play.GiveHintEvent;
import com.ckroetsch.hanabi.events.socket.play.PlayCardEvent;
import com.ckroetsch.hanabi.events.socket.common.SendMessageEvent;
import com.ckroetsch.hanabi.model.Game;
import com.ckroetsch.hanabi.model.requests.Message;
import com.ckroetsch.hanabi.model.Spectator;
import com.ckroetsch.hanabi.network.HanabiFrontEndAPI;
import com.ckroetsch.hanabi.util.JsonUtil;
import com.google.inject.Inject;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import roboguice.fragment.RoboFragment;
import roboguice.inject.InjectView;

/**
 * @author curtiskroetsch
 */
public class ChatFragment extends RoboFragment {

    private static final String TAG = ChatFragment.class.getName();

    /* Flags */
    public static final int CHAT_ME = 0;
    public static final int GAME_ME = 1;
    public static final int CHAT_YOU = 2;
    public static final int GAME_YOU = 3;

    /* View Types */
    public static final int NUM_CHAT = 4;

    String mName;

    Game mGame;

    ChatAdapter mChatAdapter;

    @InjectView(R.id.conversation)
    ListView mConversation;

    @InjectView(R.id.chat_send)
    View mSendButton;

    @InjectView(R.id.chat_body)
    EditText mChatBody;

    @Inject
    HanabiFrontEndAPI mHanabiAPI;

    Map<String, Integer> mNameColorMap;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BusSingleton.get().register(this);
        mName = getArguments().getString(TabbedHanabiFragment.KEY_NAME);
        mGame = JsonUtil.jsonToObject(getArguments().getString(TabbedHanabiFragment.KEY_GAME), Game.class);
        mNameColorMap = mapPlayersToColors();
    }

    private Map<String, Integer> mapPlayersToColors() {
        final int numSpectators = mGame.getSpectators().size();
        final int numPlayers = mGame.getPlayers().size();
        final int peopleCount = numSpectators + numPlayers;
        final Map<String, Integer> map = new HashMap<String, Integer>(peopleCount);

        int playerColors[] = getResources().getIntArray(R.array.chat_players);
        for (int i = 0; i < numPlayers; i++) {
            map.put(mGame.getPlayers().get(i).getName(), playerColors[i]);
        }

        for (Spectator spectator : mGame.getSpectators()) {
            map.put(spectator.getName(), getResources().getColor(R.color.chat_spectator));
        }

        return map;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chat, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mChatAdapter = new ChatAdapter(getActivity());
        mConversation.setAdapter(mChatAdapter);
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSendClicked();
            }
        });
    }

    private void onSendClicked() {
        final String message = mChatBody.getText().toString();
        mChatBody.getText().clear();
        mHanabiAPI.sendMessage(mName, mGame.getId(), message);
    }

    @Subscribe
    public void onMessageReceived(SendMessageEvent event) {
        Log.d(TAG, "onMesageReceived: " + event.name + " : " + event.message);
        final Message message = new Message(event.name, event.message);
        final int color = mNameColorMap.get(event.name);
        mChatAdapter.append(new MessageChatItem(getActivity(), mName, message, color));
    }

    @Subscribe
    public void onDiscard(DiscardEvent event) {
        mGame = event.game;
        mChatAdapter.append(new CardPlayedChatItem(getActivity(), mName, event));
    }

    @Subscribe
    public void onPlayCard(PlayCardEvent event) {
        mGame = event.game;
        mChatAdapter.append(new CardPlayedChatItem(getActivity(), mName, event));
    }

    @Subscribe
    public void onHintGiven(GiveHintEvent event) {
        mGame = event.game;
        mChatAdapter.append(new HintGivenChatItem(getActivity(), mName, event));
    }

    @Override
    public void onDestroy() {
        BusSingleton.get().unregister(this);
        super.onDestroy();
    }


    public class ChatAdapter extends ArrayAdapter<ChatItem> {

        public ChatAdapter(Context context, List<ChatItem> messages) {
            super(context, 0, messages);
        }

        public ChatAdapter(Context context) {
            this(context, new ArrayList<ChatItem>());
        }

        @Override
        public int getItemViewType(int position) {
            return getItem(position).getViewType();
        }

        @Override
        public int getViewTypeCount() {
            return NUM_CHAT;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            return getItem(position).getView(view, parent);
        }

        public void append(ChatItem chatItem) {
            add(chatItem);
            notifyDataSetChanged();
        }
    }

    public static CharSequence getInitials(String name) {
        return TextUtils.substring(name, 0, Math.min(name.length(), 2));
    }
}

