package com.ckroetsch.hanabi.app;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.ckroetsch.hanabi.R;
import com.ckroetsch.hanabi.events.BusSingleton;
import com.ckroetsch.hanabi.events.MessageEvent;
import com.ckroetsch.hanabi.events.game.CardDiscardedEvent;
import com.ckroetsch.hanabi.events.game.CardPlayedEvent;
import com.ckroetsch.hanabi.events.game.GameCardPlayedEvent;
import com.ckroetsch.hanabi.events.game.HintEvent;
import com.ckroetsch.hanabi.events.game.NumberHintEvent;
import com.ckroetsch.hanabi.events.game.SuitHintEvent;
import com.ckroetsch.hanabi.model.Game;
import com.ckroetsch.hanabi.model.Message;
import com.ckroetsch.hanabi.model.Spectator;
import com.ckroetsch.hanabi.util.JsonUtil;
import com.ckroetsch.hanabi.view.CardView;
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

    /* Flags */
    private static final int CHAT_FLAG = 8;
    private static final int GAME_FLAG = 4;
    private static final int ME_FLAG = 2;
    private static final int YOU_FLAG = 1;

    /* View Types */
    private static final int NUM_CHAT = 4;

    String mName;

    Game mGame;

    ChatAdapter mChatAdapter;

    @Inject
    LayoutInflater mInflater;

    @InjectView(R.id.conversation)
    ListView mConversation;

    @InjectView(R.id.chat_send)
    View mSendButton;

    @InjectView(R.id.chat_body)
    EditText mChatBody;

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
            int i;

            private String getNextName() {
                final int numSpectators = mGame.getSpectators().size();
                final int numPlayers = mGame.getPlayers().size();
                final int peopleCount = numSpectators + numPlayers;
                i = i % peopleCount;
                if (i < numPlayers) {
                    return mGame.getPlayers().get(i).getName();
                } else {
                    return mGame.getSpectators().get(i - numPlayers).getName();
                }
            }


            @Override
            public void onClick(View view) {
                final String name = getNextName();
                final Message message = new Message(name, mChatBody.getText().toString());
                BusSingleton.get().post(new MessageEvent(message));
                mChatBody.getText().clear();
                i++;
            }
        });

    }

    @Subscribe
    public void onMessageReceived(MessageEvent messageEvent) {
        mChatAdapter.add(new MessageChatItem(messageEvent.getMessage()));
        mChatAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        BusSingleton.get().unregister(this);
        super.onDestroy();
    }

    public interface ChatItem {
        View getView(View convertView, ViewGroup parent);
        int getViewType();
    }

    public class MessageChatItem implements ChatItem {

        private static final int ME_CHAT = ME_FLAG | CHAT_FLAG;
        private static final int YOU_CHAT = YOU_FLAG | CHAT_FLAG;

        Message mMesssage;

        public MessageChatItem(Message message) {
            mMesssage = message;
        }

        @Override
        public View getView(View view, ViewGroup parent) {
            ViewHolder viewHolder;
            if (view == null || view.getTag() == null) {
                viewHolder = new ViewHolder();
                view = mInflater.inflate(getViewType() == ME_CHAT ? R.layout.chat_message_mine : R.layout.chat_message_you, parent, false);
                viewHolder.mName = (TextView) view.findViewById(R.id.chat_initials);
                viewHolder.mMessage = (TextView) view.findViewById(R.id.chat_message);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }

            int color = mNameColorMap.get(mMesssage.getName());
            ((GradientDrawable) viewHolder.mName.getBackground()).setColor(color);
            viewHolder.mMessage.setText(mMesssage.getMessage());
            viewHolder.mName.setText(getInitials(mMesssage.getName()));
            return view;
        }

        @Override
        public int getViewType() {
            return mMesssage.getName().equals(mName) ? ME_CHAT : YOU_CHAT;
        }

        class ViewHolder {
            TextView mName;
            TextView mMessage;
        }
    }

    public abstract class GameChatItem implements ChatItem {

        private static final int ME_GAME = ME_FLAG | GAME_FLAG;
        private static final int YOU_GAME = YOU_FLAG | GAME_FLAG;

        @Override
        public final View getView(View view, ViewGroup parent) {
            ViewHolder viewHolder;
            if (view == null || view.getTag() == null) {
                viewHolder = new ViewHolder();
                view = mInflater.inflate((getViewType() & ME_FLAG) > 0 ? R.layout.chat_game_mine : R.layout.chat_game_you, parent, false);
                viewHolder.mName = (TextView) view.findViewById(R.id.chat_initials);
                viewHolder.mMessage = (TextView) view.findViewById(R.id.chat_message);
                viewHolder.mCard = (CardView) view.findViewById(R.id.chat_game_card);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            bindToView(viewHolder);
            return view;
        }

        @Override
        public final int getViewType() {
            return isMyChatItem() ? ME_GAME : YOU_GAME;
        }

        public abstract void bindToView(ViewHolder view);

        public abstract boolean isMyChatItem();

        class ViewHolder {
            TextView mName;
            TextView mMessage;
            CardView mCard;
        }
    }

    public class CardPlayedChatItem extends GameChatItem {

        GameCardPlayedEvent mEvent;

        public CardPlayedChatItem(GameCardPlayedEvent event) {
            mEvent = event;
        }

        @Override
        public void bindToView(ViewHolder viewHolder) {
            viewHolder.mCard.bindWithCard(mEvent.getCard());
            viewHolder.mName.setText(getInitials(mEvent.getName()));
            final int stringResId;
            if (mEvent instanceof CardDiscardedEvent) {
                stringResId = R.string.chat_game_discard;
            } else if (((CardPlayedEvent) mEvent).wasSuccessful()) {
                stringResId = R.string.chat_game_played_success;
            } else {
                stringResId = R.string.chat_game_played_fail;
            }
            viewHolder.mMessage.setText(getResources().getString(stringResId, mEvent.getName()));
        }

        @Override
        public boolean isMyChatItem() {
            return mEvent.getName().equals(mName);
        }
    }

    public class HintGivenChatItem extends GameChatItem {

        HintEvent mEvent;

        public HintGivenChatItem(HintEvent event) {
            mEvent = event;
        }

        @Override
        public void bindToView(ViewHolder viewHolder) {
            viewHolder.mName.setText(getInitials(mEvent.getHinter()));
            String message = null;
            if (mEvent instanceof SuitHintEvent) {
                message = getString(R.string.chat_game_hint_color, ((SuitHintEvent) mEvent).getSuit().toString().toLowerCase());
            } else if (mEvent instanceof NumberHintEvent) {
                message = getString(R.string.chat_game_hint_number, ((NumberHintEvent) mEvent).getNumber());
            }
            viewHolder.mMessage.setText(message);
        }

        @Override
        public boolean isMyChatItem() {
            return mEvent.getHinter().equals(mName);
        }
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
        public View getView(int position, View view, ViewGroup parent) {
            return getItem(position).getView(view, parent);
        }
    }

    private CharSequence getInitials(String name) {
        return TextUtils.substring(name, 0, Math.min(name.length(), 2));
    }
}

