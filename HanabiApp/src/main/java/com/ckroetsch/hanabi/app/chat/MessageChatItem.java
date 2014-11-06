package com.ckroetsch.hanabi.app.chat;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ckroetsch.hanabi.R;
import com.ckroetsch.hanabi.model.requests.Message;
import com.google.inject.Inject;

import roboguice.RoboGuice;

/**
* @author curtiskroetsch
*/
public class MessageChatItem implements ChatItem {

    @Inject
    LayoutInflater mInflater;

    final Message mMesssage;

    final String mName;

    final int mColor;

    public MessageChatItem(Context context, String name, Message message, int color) {
        RoboGuice.getInjector(context).injectMembersWithoutViews(this);
        mMesssage = message;
        mName = name;
        mColor = color;
    }

    @Override
    public View getView(View view, ViewGroup parent) {
        ViewHolder viewHolder;
        final int type = getViewType();

        if (view == null || view.getTag() == null || ((ViewHolder) view.getTag()).mType != type) {
            viewHolder = new ViewHolder();
            view = mInflater.inflate(type == ChatFragment.CHAT_ME ? R.layout.chat_message_mine : R.layout.chat_message_you, parent, false);
            viewHolder.mName = (TextView) view.findViewById(R.id.chat_initials);
            viewHolder.mMessage = (TextView) view.findViewById(R.id.chat_message);
            viewHolder.mType = type;
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        ((GradientDrawable) viewHolder.mName.getBackground()).setColor(mColor);
        viewHolder.mMessage.setText(mMesssage.getMessage());
        viewHolder.mName.setText(ChatFragment.getInitials(mMesssage.getName()));
        return view;
    }

    @Override
    public int getViewType() {
        return mMesssage.getName().equals(mName) ? ChatFragment.CHAT_ME : ChatFragment.CHAT_YOU;
    }

    class ViewHolder {
        int mType;
        TextView mName;
        TextView mMessage;
    }
}
