package com.ckroetsch.hanabi.app;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.ckroetsch.hanabi.R;
import com.ckroetsch.hanabi.app.chat.ChatFragment;

/**
* @author curtiskroetsch
*/
public enum HanabiTab {
    GAME(R.string.tab_title_game, GameFragment.class),
    CHAT(R.string.tab_title_chat, ChatFragment.class),
    HISTORY(R.string.tab_title_history, HistoryFragment.class);

    private int mTitleId;
    private String mClassName;
    private Fragment mFragment;

    private <T extends Fragment> HanabiTab(int title, Class<T> fragmentClass) {
        mTitleId = title;
        mClassName = fragmentClass.getName();
    }

    public Fragment getFragment(Context context, Bundle args) {
        if (mFragment == null) {
            mFragment = Fragment.instantiate(context, mClassName, args);
        }
        return mFragment;
    }

    public String getTitle(Context context) {
        return context.getString(mTitleId);
    }
}
