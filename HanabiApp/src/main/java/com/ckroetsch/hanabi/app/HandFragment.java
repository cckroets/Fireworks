package com.ckroetsch.hanabi.app;

import android.widget.LinearLayout;
import android.widget.TextView;

import com.ckroetsch.hanabi.R;

import roboguice.fragment.RoboFragment;
import roboguice.inject.InjectView;

/**
 * @author curtiskroetsch
 */
public class HandFragment extends RoboFragment {

    @InjectView(R.id.name)
    TextView mName;

    @InjectView(R.id.card_container)
    LinearLayout mCardContainer;

    public HandFragment() {

    }



}
