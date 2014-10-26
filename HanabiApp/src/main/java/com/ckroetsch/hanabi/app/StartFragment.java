package com.ckroetsch.hanabi.app;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.ckroetsch.hanabi.R;
import com.ckroetsch.hanabi.events.BusSingleton;
import com.ckroetsch.hanabi.events.socket.EnterGameEvent;
import com.ckroetsch.hanabi.events.socket.HanabiErrorEvent;
import com.ckroetsch.hanabi.events.socket.ResumeGameEvent;
import com.ckroetsch.hanabi.events.socket.SocketEvent;
import com.ckroetsch.hanabi.network.HanabiError;
import com.ckroetsch.hanabi.network.HanabiFrontEndAPI;
import com.ckroetsch.hanabi.network.HanabiSocketIO;
import com.google.inject.Inject;
import com.squareup.otto.Subscribe;

import roboguice.fragment.RoboFragment;
import roboguice.inject.InjectView;

/**
 * @author curtiskroetsch
 */
public class StartFragment extends RoboFragment implements
        CreateGameDialogFragment.CreateDialogListener,
        EnterGameDialogFragment.EnterDialogListener {

    public static final String TAG = StartFragment.class.getName();

    @InjectView(R.id.start_create_button)
    Button mCreateButton;

    @InjectView(R.id.start_enter_button)
    Button mEnterButton;

    @InjectView(R.id.start_resume_button)
    Button mResumeButton;

    @Inject
    HanabiFrontEndAPI mHanabiAPI;

    DialogInterface mDialogInterface;

    boolean mResume;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BusSingleton.get().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_start, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCreateDialog();
            }
        });
        mEnterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mResume = false;
                showEnterDialog();
            }
        });
        mResumeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mResume = true;
                showEnterDialog();
            }
        });
    }

    public void showCreateDialog() {
        final DialogFragment createDialog = CreateGameDialogFragment.createInstance(this);
        createDialog.show(getActivity().getSupportFragmentManager(), "create");
    }

    public void showEnterDialog() {
        final DialogFragment enterDialog = EnterGameDialogFragment.createInstance(this);
        enterDialog.show(getActivity().getSupportFragmentManager(), "enter");
    }

    @Subscribe
    public void onGameEnter(EnterGameEvent event) {
        Log.d(TAG, "Enter success: " + event.game.getId());
        ((MainActivity) getActivity()).openGameFragment(event.game, event.name);
    }

    private void onGameEnterFailed() {
        if (mDialogInterface != null) {
            mDialogInterface.dismiss();
        }
    }

    @Subscribe
    public void onGameResume(ResumeGameEvent event) {
        Log.d(TAG, "Resume success: " + event.game.getId());
        ((MainActivity) getActivity()).openGameFragment(event.game, event.name);
    }

    @Override
    public void onGameCreate(final String name, boolean rainbow, DialogInterface dialogInterface) {
        mDialogInterface = dialogInterface;
        mHanabiAPI.createGame(name, rainbow);
    }

    @Override
    public void onGameEnter(int id, final String name, final DialogInterface dialogInterface) {
        mDialogInterface = dialogInterface;
        Log.d(TAG, "ENTERED: id = " + id + " name = " + name);
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(getActivity(), "Name must not be empty.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mResume) {
            mHanabiAPI.resumeGame(name, id);
        } else {
            mHanabiAPI.enterGame(name, id);
        }
    }

    @Override
    public void onCancel(DialogInterface dialogInterface) {
        Log.d(TAG, "CANCELLED");
        dialogInterface.dismiss();
    }

    @Subscribe
    public void onFailure(HanabiErrorEvent event) {
        final HanabiError error = event.getError();
        final String eventString = event.getError().getEvent();
        final SocketEvent socketEvent = SocketEvent.getEvent(eventString);
        if (socketEvent == null) {
            Log.e(TAG, "Unknown error event : " + eventString);
            return;
        } else if (socketEvent == SocketEvent.ENTER_GAME) {
            onGameEnterFailed();
        }
        Log.e(TAG, error.getEvent() + ":" + error.getReason());
        Toast.makeText(getActivity(), error.getReason(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        BusSingleton.get().unregister(this);
        super.onDestroy();
    }
}
