package com.ckroetsch.hanabi.app;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ckroetsch.hanabi.R;

import roboguice.fragment.RoboFragment;

/**
 * @author curtiskroetsch
 */
public class HistoryFragment extends RoboFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history, container, false);
    }
}
