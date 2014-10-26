package com.ckroetsch.hanabi.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;
import com.ckroetsch.hanabi.R;
import com.ckroetsch.hanabi.model.Game;
import com.ckroetsch.hanabi.util.JsonUtil;

import org.json.JSONObject;

import roboguice.fragment.RoboFragment;
import roboguice.inject.InjectView;

/**
 * @author curtiskroetsch
 */
public class TabbedHanabiFragment extends RoboFragment {

    public static final String KEY_GAME_ID = "game_id";
    public static final String KEY_GAME = "game";
    public static final String KEY_NAME = "name";

    @InjectView(R.id.pager)
    ViewPager mViewPager;

    @InjectView(R.id.tabs)
    PagerSlidingTabStrip mTabStrip;

    public static TabbedHanabiFragment createInstance(Game game, String name) {
        final TabbedHanabiFragment fragment = new TabbedHanabiFragment();
        final JSONObject gameJSON = JsonUtil.objectToJson(game);
        final Bundle args = new Bundle();
        args.putString(KEY_GAME, gameJSON.toString());
        args.putString(KEY_NAME, name);
        args.putInt(KEY_GAME_ID, game.getId());
        fragment.setArguments(args);
        return fragment;
    }

        @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tabs, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewPager.setAdapter(new HanabiViewPagerAdapter(getFragmentManager()));
        mTabStrip.setViewPager(mViewPager);
    }

    public class HanabiViewPagerAdapter extends FragmentPagerAdapter {

        public HanabiViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            HanabiTab tab = HanabiTab.values()[position];
            return tab.getFragment(getActivity(), getArguments());
        }

        @Override
        public CharSequence getPageTitle(int position) {
            HanabiTab tab = HanabiTab.values()[position];
            return tab.getTitle(getActivity());
        }

        @Override
        public int getCount() {
            return HanabiTab.values().length;
        }
    }
}
