package com.ckroetsch.hanabi.app;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.ckroetsch.hanabi.R;
import roboguice.activity.RoboFragmentActivity;


public class MainActivity extends RoboFragmentActivity {

    private static final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new StartFragment())
                .commit();
    }

}
