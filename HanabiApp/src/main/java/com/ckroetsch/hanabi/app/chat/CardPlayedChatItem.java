package com.ckroetsch.hanabi.app.chat;

import android.content.Context;

import com.ckroetsch.hanabi.R;
import com.ckroetsch.hanabi.events.socket.CardEvent;
import com.ckroetsch.hanabi.events.socket.DiscardEvent;
import com.ckroetsch.hanabi.model.Card;

/**
* @author curtiskroetsch
*/
public class CardPlayedChatItem extends GameChatItem {

    CardEvent mEvent;

    String mName;

    Context mContext;

    public CardPlayedChatItem(Context context, String name, CardEvent event) {
        super(context);
        mContext = context;
        mEvent = event;
        mName = name;
    }

    @Override
    public void bindToView(ViewHolder viewHolder) {
        viewHolder.mCard.bindWithCard(mEvent.card);
        viewHolder.mName.setText(ChatFragment.getInitials(mEvent.name));
        final int stringResId;
        if (mEvent instanceof DiscardEvent) {
            stringResId = R.string.chat_game_discard;
        } else {
            stringResId = R.string.chat_game_played_success;
        }
        viewHolder.mMessage.setText(mContext.getResources().getString(stringResId, mEvent.name));
    }

    @Override
    public boolean isMyChatItem() {
        return mEvent.name.equals(mName);
    }
}
