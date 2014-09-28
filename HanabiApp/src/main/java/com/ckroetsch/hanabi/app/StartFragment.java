package com.ckroetsch.hanabi.app;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ckroetsch.hanabi.R;
import com.ckroetsch.hanabi.model.Game;
import com.ckroetsch.hanabi.model.GameResponse;
import com.ckroetsch.hanabi.model.GameId;
import com.ckroetsch.hanabi.network.HanabiFrontEndAPI;
import com.google.inject.Inject;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
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

    @Inject
    HanabiFrontEndAPI mHanabiAPI;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    @Override
    public void onGameCreate(String name, boolean rainbow) {
        Log.d(TAG, "CREATED: name = " + name + " rainbow = " + rainbow);
        mHanabiAPI.create(name, rainbow, new Callback<Game>() {
            @Override
            public void success(Game game, Response response) {
                Log.d(TAG, "Game create() : id = " + game.getId());
                ((MainActivity) getActivity()).openGameFragment(game);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e(TAG, "error: " + error.getMessage());
            }
        });
    }

    @Override
    public void onGameEnter(int id, String name) {
        Log.d(TAG, "ENTERED: id = " + id + " name = " + name);
        mHanabiAPI.enter(id, name, new Callback<GameResponse>() {
            @Override
            public void success(GameResponse response, Response networkResponse) {
                if (response.isSuccess()) {
                    Log.d(TAG, "Enter success: " + response.getGame().getLives());
                    ((MainActivity) getActivity()).openGameFragment(response.getGame());

                } else {
                    Log.d(TAG, "Enter failed");
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG, "Enter error: " + error.getMessage());
            }
        });
    }

    @Override
    public void onCancel() {
        Log.d(TAG, "CANCELLED");
    }
}
