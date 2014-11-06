package com.ckroetsch.hanabi.app.chat;

import android.content.Context;
import android.view.View;

import com.ckroetsch.hanabi.R;
import com.ckroetsch.hanabi.events.socket.play.GiveHintEvent;

/**
* @author curtiskroetsch
*/
public class HintGivenChatItem extends GameChatItem {

    GiveHintEvent mEvent;
    String mName;

    Context mContext;

    public HintGivenChatItem(Context context, String name, GiveHintEvent event) {
        super(context);
        mContext = context;
        mEvent = event;
        mName = name;
    }

    @Override
    public void bindToView(ViewHolder viewHolder) {
        viewHolder.mCard.setVisibility(View.GONE);
        viewHolder.mName.setText(ChatFragment.getInitials(mEvent.from));
        final int resId = mEvent.hintType.equals(GiveHintEvent.NUMBER) ?
                R.string.chat_game_hint_number : R.string.chat_game_hint_color;
        viewHolder.mMessage.setText(mContext.getString(resId, mEvent.to, mEvent.hint));
        viewHolder.mCard.setVisibility(View.GONE);
    }

    @Override
    public boolean isMyChatItem() {
        return mEvent.from.equals(mName);
    }
}
