package com.ckroetsch.hanabi.app.chat;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ckroetsch.hanabi.R;
import com.ckroetsch.hanabi.view.CardView;
import com.google.inject.Inject;

import roboguice.RoboGuice;

/**
* @author curtiskroetsch
*/
public abstract class GameChatItem implements ChatItem {

    private Activity mContext;

    @Inject
    protected LayoutInflater mInflater;

    public GameChatItem(Context context) {
        mContext = (Activity) context;
        RoboGuice.getInjector(context).injectMembersWithoutViews(this);
    }

    @Override
    public final View getView(View view, ViewGroup parent) {
        ViewHolder viewHolder;
        final int type = getViewType();

        if (view == null || view.getTag() == null || ((ViewHolder)view.getTag()).mType != type) {
            viewHolder = new ViewHolder();
            view = mInflater.inflate(type == ChatFragment.GAME_ME ? R.layout.chat_game_mine : R.layout.chat_game_you, parent, false);
            viewHolder.mName = (TextView) view.findViewById(R.id.chat_initials);
            viewHolder.mMessage = (TextView) view.findViewById(R.id.chat_message);
            FrameLayout cardHolder = (FrameLayout) view.findViewById(R.id.card_holder);
            viewHolder.mCard = CardView.createCard(mContext, mInflater, cardHolder, CardView.Size.SMALL);
            cardHolder.addView(viewHolder.mCard);
            viewHolder.mType = type;
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        bindToView(viewHolder);
        return view;
    }

    @Override
    public final int getViewType() {
        return isMyChatItem() ? ChatFragment.GAME_ME : ChatFragment.GAME_YOU;
    }

    public abstract void bindToView(ViewHolder view);

    public abstract boolean isMyChatItem();

    class ViewHolder {
        int mType;
        TextView mName;
        TextView mMessage;
        CardView mCard;
    }
}
