package com.ckroetsch.hanabi.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.ckroetsch.hanabi.R;
import com.ckroetsch.hanabi.model.Game;

import roboguice.activity.RoboFragmentActivity;


public class MainActivity extends RoboFragmentActivity {

    private static final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            openStartFragment();
        }
    }

    public void openStartFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new StartFragment())
                .commit();
    }

    public void openGameFragment(Game game, String name) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment gameFragment = TabbedHanabiFragment.createInstance(game, name);
        transaction.replace(R.id.fragment_container, gameFragment)
                .commit();
    }

}
