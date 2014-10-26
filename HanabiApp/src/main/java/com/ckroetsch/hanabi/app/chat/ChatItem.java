package com.ckroetsch.hanabi.app.chat;

import android.view.View;
import android.view.ViewGroup;

/**
* @author curtiskroetsch
*/
public interface ChatItem {
    View getView(View convertView, ViewGroup parent);
    int getViewType();
}
